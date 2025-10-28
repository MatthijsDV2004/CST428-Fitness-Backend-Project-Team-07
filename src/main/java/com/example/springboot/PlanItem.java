package com.example.springboot;

import jakarta.persistence.*;

@Entity
@Table(name = "planItems")
public class PlanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanItemID")
    private Long id;

    @Column(name = "PlanID")
    private Long planId;

    @Column(name = "WorkoutID")
    private Integer workoutId;

    @Column(name = "Day")
    private String day;

    @Column(name = "Sets")
    private int sets;

    @Column(name = "Reps")
    private int reps;

    public PlanItem() {}

    public PlanItem(Long planId, Integer workoutId, String day, int sets, int reps) {
        this.planId = planId;
        this.workoutId = workoutId;
        this.day = day;
        this.sets = sets;
        this.reps = reps;
    }

    public Long getId() { return id; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public Integer getWorkoutId() { return workoutId; }
    public void setWorkoutId(Integer workoutId) { this.workoutId = workoutId; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
}
