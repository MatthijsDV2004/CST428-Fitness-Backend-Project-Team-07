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
	@PostMapping("/plans/{planId}/addExercise")
	public ResponseEntity<String> addExerciseToPlan(
			@PathVariable Long planId,
			@RequestBody Map<String, Object> exerciseData) {

		String name = (String) exerciseData.get("name");
		Integer sets = (Integer) exerciseData.get("sets");
		Integer reps = (Integer) exerciseData.get("reps");

		System.out.println("Adding exercise " + name + " (" + sets + "x" + reps + ") to plan ID " + planId);

		// Find workout by name
		var workoutOpt = workoutRepository.findAll()
				.stream()
				.filter(w -> w.getWorkoutName().equalsIgnoreCase(name))
				.findFirst();

		Integer workoutId = workoutOpt.map(w -> w.getWorkoutID()).orElse(null);

		if (workoutId == null) {
			return ResponseEntity.badRequest().body("Workout not found: " + name);
		}

		// Create and save PlanItem
		PlanItem planItem = new PlanItem(planId, workoutId, "Default", sets, reps);
		planItemRepository.save(planItem);

		return ResponseEntity.ok("Exercise added to plan " + planId);
	}
	@DeleteMapping("/plans/{planId}/exercise/{name}")
	public ResponseEntity<String> deleteExercise(
			@PathVariable Long planId,
			@PathVariable String name) {
		// delete DB record
		return ResponseEntity.ok("Exercise deleted");
	}
	@PutMapping("/plans/{planId}/exercise/{name}")
	public ResponseEntity<String> updateExercise(
			@PathVariable Long planId,
			@PathVariable String name,
			@RequestBody Map<String, Object> body) {
		Integer sets = (Integer) body.get("sets");
		Integer reps = (Integer) body.get("reps");
		// update DB record here
		return ResponseEntity.ok("Exercise updated");
	}
	@GetMapping("/plans/day")
	public List<Map<String, Object>> getPlansByDay(@RequestParam String googleId, @RequestParam String day) {
		List<Plan> plans = planRepository.findByGoogleIdAndDay(googleId, day);
		List<Map<String, Object>> enrichedPlans = new ArrayList<>();

		for (Plan plan : plans) {
			List<PlanItem> items = planItemRepository.findByPlanId(plan.getId());
			Map<String, Object> data = new HashMap<>();
			data.put("id", plan.getId());
			data.put("googleId", plan.getGoogleId());
			data.put("name", plan.getName());
			data.put("day", plan.getDay());
			data.put("exercises", items);
			enrichedPlans.add(data);
		}

		return enrichedPlans;
	}

	@PostMapping("/plans")
	public Plan createPlan(@RequestBody Plan newPlan) {
		return planRepository.save(newPlan);
	}

	@PutMapping("/plans/{id}")
	public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @RequestBody Plan updatedPlan) {
		Optional<Plan> optionalPlan = planRepository.findById(id);
		if (optionalPlan.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Plan plan = optionalPlan.get();
		plan.setName(updatedPlan.getName());
		plan.setDay(updatedPlan.getDay());
		plan.setGoogleId(updatedPlan.getGoogleId());
		planRepository.save(plan);
		return ResponseEntity.ok(plan);
	}

	@DeleteMapping("/plans/{id}")
	public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
		if (planRepository.existsById(id)) {
			planRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/getWorkouts")
	public List<Workout> getWorkouts() {
		try {
			System.out.println("Fetching workouts...");
			List<Workout> workouts = workoutRepository.findAll();
			System.out.println("Workouts fetched: " + workouts.size());
			return workouts;
		} catch (Exception e) {
			System.err.println("ERROR fetching workouts:");
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
