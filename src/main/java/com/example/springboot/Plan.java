package com.example.springboot;

import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanID")
    private Long id;

    @Column(name = "GoogleID", nullable = false)
    private String googleId;

    @Column(name = "PlanName", nullable = false)
    private String name;

    @Column(name = "Day", nullable = false)
    private String day;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGoogleId() { return googleId; }
    public void setGoogleId(String googleId) { this.googleId = googleId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
}
