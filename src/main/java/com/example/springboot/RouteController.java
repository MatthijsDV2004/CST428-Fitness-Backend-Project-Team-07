package com.example.springboot;

import com.example.springboot.model.DBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
public class RouteController {

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private PlanItemRepository planItemRepository;

	@Autowired
	private WorkoutRepository workoutRepository;

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/getUserById")
	public DBUser getUserById(@RequestParam(defaultValue = "0") int userid) {
		DBUser newUser = new DBUser(0, "", "", "", "");
		String sql = "SELECT * FROM Users WHERE UserID = " + userid;
		RowMapper<DBUser> rowMapper = (rs, rowNum) -> new DBUser(
				rs.getInt("UserID"),
				rs.getString("FirstName"),
				rs.getString("LastName"),
				rs.getString("Email"),
				rs.getString("Password")
		);
		return newUser;
	}

	@GetMapping("/plans")
	public List<Plan> getPlans(@RequestParam String googleId) {
		return planRepository.findByGoogleId(googleId);
	}

	@GetMapping("/plans/day")
	public List<Map<String, Object>> getPlansByDay(@RequestParam String googleId, @RequestParam String day) {
		List<Plan> plans = planRepository.findByGoogleIdAndDay(googleId, day);
		List<Map<String, Object>> enrichedPlans = new ArrayList<>();

		for (Plan plan : plans) {
			List<PlanItem> items = planItemRepository.findByPlanId(plan.getId());
			List<Map<String, Object>> exercises = new ArrayList<>();

			for (PlanItem item : items) {
				Map<String, Object> exercise = new HashMap<>();
				exercise.put("id", item.getId());
				exercise.put("sets", item.getSets());
				exercise.put("reps", item.getReps());
				exercise.put("day", item.getDay());
				exercise.put("workoutId", item.getWorkoutId());

				workoutRepository.findById(item.getWorkoutId()).ifPresent(workout ->
						exercise.put("name", workout.getWorkoutName())
				);

				exercises.add(exercise);
			}

			Map<String, Object> planData = new HashMap<>();
			planData.put("id", plan.getId());
			planData.put("googleId", plan.getGoogleId());
			planData.put("name", plan.getName());
			planData.put("day", plan.getDay());
			planData.put("exercises", exercises);

			enrichedPlans.add(planData);
		}

		return enrichedPlans;
	}

	@PostMapping("/plans")
	public Plan createPlan(@RequestBody Plan newPlan) {
		return planRepository.save(newPlan);
	}

	@PutMapping("/plans/{id}")
	public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @RequestBody Plan updatedPlan) {
		return planRepository.findById(id)
				.map(plan -> {
					plan.setName(updatedPlan.getName());
					plan.setDay(updatedPlan.getDay());
					plan.setGoogleId(updatedPlan.getGoogleId());
					planRepository.save(plan);
					return ResponseEntity.ok(plan);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/plans/{id}")
	public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
		if (planRepository.existsById(id)) {
			planRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/plans/{planId}/addExercise")
	public ResponseEntity<String> addExerciseToPlan(@PathVariable Long planId, @RequestBody Map<String, Object> exerciseData) {
		String name = (String) exerciseData.get("name");
		Integer sets = (Integer) exerciseData.get("sets");
		Integer reps = (Integer) exerciseData.get("reps");

		var workoutOpt = workoutRepository.findAll()
				.stream()
				.filter(w -> w.getWorkoutName().equalsIgnoreCase(name))
				.findFirst();

		Integer workoutId = workoutOpt.map(Workout::getWorkoutID).orElse(null);
		if (workoutId == null) return ResponseEntity.badRequest().body("Workout not found: " + name);

		PlanItem planItem = new PlanItem(planId, workoutId, "Default", sets, reps);
		planItemRepository.save(planItem);

		return ResponseEntity.ok("Exercise added to plan " + planId);
	}

	@PutMapping("/plans/{planId}/exercise/{name}")
	public ResponseEntity<String> updateExercise(@PathVariable Long planId, @PathVariable String name, @RequestBody Map<String, Object> body) {
		Integer sets = (Integer) body.get("sets");
		Integer reps = (Integer) body.get("reps");

		var workoutOpt = workoutRepository.findAll()
				.stream()
				.filter(w -> w.getWorkoutName().equalsIgnoreCase(name))
				.findFirst();

		Integer workoutId = workoutOpt.map(Workout::getWorkoutID).orElse(null);
		if (workoutId == null) return ResponseEntity.badRequest().body("Workout not found: " + name);

		List<PlanItem> items = planItemRepository.findByPlanId(planId);
		for (PlanItem item : items) {
			if (Objects.equals(item.getWorkoutId(), workoutId)) {
				item.setSets(sets);
				item.setReps(reps);
				planItemRepository.save(item);
				return ResponseEntity.ok("Exercise updated");
			}
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/plans/{planId}/exercise/{name}")
	public ResponseEntity<String> deleteExercise(@PathVariable Long planId, @PathVariable String name) {
		var workoutOpt = workoutRepository.findAll()
				.stream()
				.filter(w -> w.getWorkoutName().equalsIgnoreCase(name))
				.findFirst();

		Integer workoutId = workoutOpt.map(Workout::getWorkoutID).orElse(null);
		if (workoutId == null) return ResponseEntity.badRequest().body("Workout not found: " + name);

		List<PlanItem> items = planItemRepository.findByPlanId(planId);
		for (PlanItem item : items) {
			if (Objects.equals(item.getWorkoutId(), workoutId)) {
				planItemRepository.delete(item);
				return ResponseEntity.ok("Exercise deleted");
			}
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/getWorkouts")
	public List<Workout> getWorkouts(@RequestParam(required = false) String name) {
		try {
			System.out.println("📢 Fetching workouts...");

			List<Workout> workouts;
			if (name != null && !name.isBlank()) {
				workouts = workoutRepository.findByWorkoutNameStartingWithIgnoreCase(name);
				System.out.println("✅ Filtered workouts starting with: " + name + " → " + workouts.size());
			} else {
				workouts = workoutRepository.findAll();
				System.out.println("✅ All workouts fetched: " + workouts.size());
			}

			return workouts;
		} catch (Exception e) {
			System.err.println("❌ ERROR fetching workouts:");
			e.printStackTrace();
			throw e;
		}
	}

	@GetMapping("/getWorkouts/{id}")
	public ResponseEntity<Workout> getWorkoutById(@PathVariable Integer id) {
		Optional<Workout> workout = workoutRepository.findById(id);
		return workout.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}
}
