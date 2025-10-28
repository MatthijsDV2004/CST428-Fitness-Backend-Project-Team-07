    package com.example.springboot;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    List<Workout> findByWorkoutNameStartingWithIgnoreCase(String name);
    List<Workout> findByMuscleGroupIgnoreCase(String muscle);
    List<Workout> findByWorkoutNameStartingWithIgnoreCaseAndMuscleGroupIgnoreCase(String name, String muscle);

}
