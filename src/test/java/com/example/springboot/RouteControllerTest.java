package com.example.springboot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RouteControllerTest {

    @Mock
    private PlanRepository planRepository;

    @Mock
    private PlanItemRepository planItemRepository;

    @Mock
    private WorkoutRepository workoutRepository;

    @InjectMocks
    private RouteController routeController;

    private Plan plan;
    private Workout workout;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        plan = new Plan();
        plan.setId(1L);
        plan.setGoogleId("123");
        plan.setDay("Monday");
        plan.setName("Chest Day");

        workout = new Workout();
        workout.setWorkoutID(1);
        workout.setWorkoutName("Bench Press");
        workout.setMuscleGroup("Chest");
    }

    @Test
    void testGetPlansByDay_ReturnsData() {
        when(planRepository.findByGoogleIdAndDay("123", "Monday"))
                .thenReturn(List.of(plan));
        when(planItemRepository.findByPlanId(1L))
                .thenReturn(List.of());

        List<Map<String, Object>> result = routeController.getPlansByDay("123", "Monday");

        assertEquals(1, result.size());
        assertEquals("Chest Day", result.get(0).get("name"));
    }

    @Test
    void testAddExerciseToPlan_Success() {
        when(workoutRepository.findAll()).thenReturn(List.of(workout));

        Map<String, Object> body = new HashMap<>();
        body.put("name", "Bench Press");
        body.put("sets", 3);
        body.put("reps", 10);

        ResponseEntity<String> result = routeController.addExerciseToPlan(1L, body);

        assertEquals(200, result.getStatusCodeValue());
        assertTrue(result.getBody().contains("Exercise added"));
    }

    @Test
    void testAddExerciseToPlan_WorkoutNotFound() {
        when(workoutRepository.findAll()).thenReturn(Collections.emptyList());
        Map<String, Object> body = Map.of("name", "Unknown", "sets", 3, "reps", 10);

        ResponseEntity<String> result = routeController.addExerciseToPlan(1L, body);

        assertEquals(400, result.getStatusCodeValue());
    }
}
