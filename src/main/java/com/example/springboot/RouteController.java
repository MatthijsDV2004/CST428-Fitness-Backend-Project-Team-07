package com.example.springboot;

import com.example.springboot.model.DBUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping
public class RouteController {

	@Autowired
	private PlanRepository planRepository;

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

		// TODO: save this exercise to your DB (create PlanItem entity later)
		return ResponseEntity.ok("Exercise added to plan " + planId);
	}
	@GetMapping("/plans/day")
	public List<Plan> getPlansByDay(@RequestParam String googleId, @RequestParam String day) {
		return planRepository.findByGoogleIdAndDay(googleId, day);
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
