package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

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
	public String getWorkouts(){
		return "This will eventually let us get a list of possible workouts.";
	}

	@GetMapping("/addWorkout")
	public String addWorkout(){
		return "This will eventually let us add workouts to the database.";
	}
}
