package com.example.springboot;

import com.example.springboot.model.DBUser;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;



@RestController
public class RouteController {
	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private WorkoutRepository workoutRepository;

		
	/** 
	 * Default route. We may or may not find a use
	 * @return nothing
	*/
	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	/**
	 * Return information for a user from the users table.
	 * @return a User; null if no user found
	 **/
	@GetMapping("/getUserById")
	public DBUser getUserById(@RequestParam(defaultValue = "0") int userid) {
		DBUser newUser = new DBUser(0,"","","","");

		String sql = "SELECT * FROM Users WHERE UserID = " + userid;


    	RowMapper<DBUser> rowMapper = (rs, rowNum) -> new DBUser(
        	rs.getInt("UserID"),
        	rs.getString("FirstName"),
        	rs.getString("LastName"),
        	rs.getString("Email"),
        	rs.getString("Password")
    	);

    	/*try {
    		DBUser user = jdbcTemplate.queryForObject(sql, rowMapper, userid);
        	return ResponseEntity.ok(user);
    	} catch (Exception e) {
        	return null;
    	}*/
		return (newUser);
	}

	/**
	 * Return information from the workoutplan table.
	 * @return an array of strings representing database rows for workout plans
	 **/
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

	/**
	 * Remove workout plans from the database.
	 * @return the number of removed rows, -1 if there's an error
	 **/
	@CrossOrigin(origins = "*")
	@GetMapping("/removeWorkoutPlan")
	public int removeWorkoutPlan(){
		return -1; //Error: this hasn't been implemented
	}
	/**
	 * Retrieve workouts from the workouts table.
	 * @return the requested rows
	 **/
//	@CrossOrigin(origins = "*")
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
//	@CrossOrigin(origins = "*")
	@GetMapping("/getWorkouts/{id}")
	public ResponseEntity<Workout> getWorkoutById(@PathVariable Integer id) {
		Optional<Workout> workout = workoutRepository.findById(id);
		return workout.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	@CrossOrigin(origins = "*")
	@GetMapping("/addWorkout")
	public boolean addWorkout(){
		return false; //not implemented yet so always returns false
	}
}
