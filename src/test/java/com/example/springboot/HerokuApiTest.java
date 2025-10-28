package com.example.springboot;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HerokuApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://cst438-d5640ff12bdc.herokuapp.com";
    }

    @Test
    void testPing() {
        when()
                .get("/ping")
                .then()
                .statusCode(200)
                .body(equalTo("pong"));
    }

    @Test
    void testGetWorkouts() {
        when()
                .get("/getWorkouts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
}
