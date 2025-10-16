package com.example.springboot;

import jakarta.persistence.*;

@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @Column(name = "WorkoutID")
    private Integer workoutID;

    @Column(name = "WorkoutName")
    private String workoutName;

    @Column(name = "WorkoutDesc")
    private String workoutDesc;

    @Column(name = "MuscleGroup")
    private String muscleGroup;

    // Getters and setters
    public Integer getWorkoutID() { return workoutID; }
    public void setWorkoutID(Integer workoutID) { this.workoutID = workoutID; }

    public String getWorkoutName() { return workoutName; }
    public void setWorkoutName(String workoutName) { this.workoutName = workoutName; }

    public String getWorkoutDesc() { return workoutDesc; }
    public void setWorkoutDesc(String workoutDesc) { this.workoutDesc = workoutDesc; }

    public String getMuscleGroup() { return muscleGroup; }
    public void setMuscleGroup(String muscleGroup) { this.muscleGroup = muscleGroup; }
}
