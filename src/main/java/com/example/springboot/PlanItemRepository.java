package com.example.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanItemRepository extends JpaRepository<PlanItem, Long> {
    List<PlanItem> findByPlanId(Long planId);
}
