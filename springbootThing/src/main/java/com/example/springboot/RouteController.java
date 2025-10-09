package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

	/** 
	 * Default route. We may or may not find a use
	 * @return nothing
	*/
	@GetMapping("/")
	public String index() {
		return "This is the basic index route.";
	}

	@GetMapping("/getUserInfo")

	/**
	 * Return information for a user from the users table.
	 * @return an array of strings representing database rows for users
	 **/
	@GetMapping("/getUsers")
	public String getUsers(int id) {
		return "This will eventually do something with Users.";
	}

	/**
	 * Return information from the workoutplan table.
	 * @return an array of strings representing database rows for workout plans
	 **/
	@GetMapping("/getWorkoutPlan")
	public String getWorkoutPlan() {
		return "This will eventually get workout plans for a user by ID.";
	}

	/**
	 * Add information to the workoutplan table.
	 * @return TRUE if successful, FALSE if there's an issue
	 **/
	@GetMapping("/createWorkoutPlan")
	public boolean createWorkoutPlan(){
		return false; //There is an issue: this hasn't been implemented yet
	}

	/**
	 * Update rows in the workoutplan table.
	 * @return TRUE if successful, FALSE if there's an issue
	 **/
	@GetMapping("/editWorkoutPlan")
	public boolean editWorkoutPlan(){
		return false; //There is an issue: this hasn't been implemented yet
	}

	/**
	 * Remove workout plans from the database.
	 * @return the number of removed rows, -1 if there's an error
	 **/
	@GetMapping("/removeWorkoutPlan")
	public int removeWorkoutPlan(){
		return -1; //Error: this hasn't been implemented
	}
	/**
	 * Retrieve workouts from the workouts table.
	 * @return the requested rows
	 **/
	@GetMapping("/getWorkouts")
	public String getWorkouts(){
		return "This will eventually let us get a list of possible workouts.";
	}

	/**
	 * Add new workouts to the workouts table.
	 * @return TRUE if successful, FALSE if not
	 **/
	@GetMapping("/addWorkout")
	public String addWorkout(){
		return false; //not implemented yet so always returns false
	}
}
