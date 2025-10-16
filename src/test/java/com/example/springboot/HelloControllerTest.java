package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
<<<<<<< HEAD
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
=======
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
>>>>>>> origin/main

import static org.assertj.core.api.Assertions.assertThat;

<<<<<<< HEAD
	@Autowired
	private MockMvc mvc;
	

    @MockBean
    private JdbcTemplate jdbcTemplate;
=======
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
})
class HelloControllerTest {
>>>>>>> origin/main

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    // ✅ Mock the repositories that RouteController depends on
    @MockBean
    private PlanRepository planRepository;

    @MockBean
    private WorkoutRepository workoutRepository;

    @Test
    void getHello() {
        String url = "http://localhost:" + port + "/";
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isEqualTo("This is the basic index route.");
    }
}
