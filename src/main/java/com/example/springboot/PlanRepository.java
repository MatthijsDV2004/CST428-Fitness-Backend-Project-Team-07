package com.example.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByGoogleId(String googleId);
    List<Plan> findByGoogleIdAndDay(String googleId, String day);
}
