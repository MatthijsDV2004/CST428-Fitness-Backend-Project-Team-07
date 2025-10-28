package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PlanRepositoryTest {

    @Autowired
    private PlanRepository planRepository;

    @Test
    void testSaveAndFindByGoogleId() {
        Plan plan = new Plan();
        plan.setGoogleId("g123");
        plan.setDay("Monday");
        plan.setName("Push Day");
        planRepository.save(plan);

        List<Plan> found = planRepository.findByGoogleId("g123");
        assertEquals(1, found.size());
        assertEquals("Push Day", found.get(0).getName());
    }

    @Test
    void testFindByGoogleIdAndDay() {
        Plan plan = new Plan();
        plan.setGoogleId("g999");
        plan.setDay("Tuesday");
        plan.setName("Leg Day");
        planRepository.save(plan);

        List<Plan> found = planRepository.findByGoogleIdAndDay("g999", "Tuesday");
        assertEquals(1, found.size());
        assertEquals("Leg Day", found.get(0).getName());
    }
}
