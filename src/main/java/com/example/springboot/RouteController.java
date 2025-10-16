package com.example.springboot;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;



@RestController
public class RouteController {
	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private WorkoutRepository workoutRepository;

	@GetMapping("/")
	public String index() {
		return "This is the basic index route.";
	}

	@GetMapping("/getUserInfo")
	public String getUsers() {
		return "This will eventually do something with Users.";
	}

	@GetMapping("/getWorkoutPlan")
	public List<Plan> getWorkoutPlan(@RequestParam int userID) {
        return planRepository.findByUserId(userID);
    }

	@PostMapping("/createWorkoutPlan")
	public Plan createWorkoutPlan(@RequestBody Plan newPlan) {
		return planRepository.save(newPlan);
	}

	@PutMapping("/editWorkoutPlan")
	public Plan editWorkoutPlan(@RequestBody Plan editPlan) {
		return planRepository.save(editPlan);
	}

	@GetMapping("/getWorkouts")
	public List<Workout> getWorkouts() {
		try {
			System.out.println("📢 Fetching workouts...");
			List<Workout> workouts = workoutRepository.findAll();
			System.out.println("✅ Workouts fetched: " + workouts.size());
			return workouts;
		} catch (Exception e) {
			System.err.println("❌ ERROR fetching workouts:");
			e.printStackTrace(); // will print the real reason in the IntelliJ console
			throw e;
		}
	}
	@GetMapping("/addWorkout")
	public String addWorkout(){
		return "This will eventually let us add workouts to the database.";
	}
}
