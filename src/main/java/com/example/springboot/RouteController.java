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
	public String getWorkoutPlan() {
		return "This will eventually get workout plans.";
	}

	@GetMapping("/createWorkoutPlan")
	public String createWorkoutPlan(){
		return "This will eventually let us add new workout plans to the database.";
	}

	@GetMapping("/editWorkoutPlan")
	public String editWorkoutPlan(){
		return "This will eventually let us edit workout plans";
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
