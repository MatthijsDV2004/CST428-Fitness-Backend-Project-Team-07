package com.example.springboot;

import com.example.springboot.auth.AuthController;
import com.example.springboot.auth.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(AuthController.class)
public class AuthControllerOAuth2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private GoogleIdTokenVerifier verifier;

    private GoogleIdToken mockIdToken;
    private GoogleIdToken.Payload mockPayload;

    @BeforeEach
    void setup() {
        mockIdToken = Mockito.mock(GoogleIdToken.class);
        mockPayload = new GoogleIdToken.Payload();
        mockPayload.setEmail("test@example.com");
        mockPayload.set("name", "Matthijs");
        mockPayload.set("picture", "https://example.com/pic.jpg");

        when(mockIdToken.getPayload()).thenReturn(mockPayload);
    }

    @Test
    void shouldRejectInvalidGoogleToken() throws Exception {
        when(verifier.verify("invalid_token")).thenReturn(null);

        mockMvc.perform(post("/api/auth/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"invalid_token\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid Google token"));
    }

    @Test
    void shouldAcceptValidGoogleTokenAndReturnJwt() throws Exception {
        when(verifier.verify("valid_token")).thenReturn(mockIdToken);
        when(jwtService.generateToken("test@example.com")).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/api/auth/google")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"token\": \"valid_token\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("mocked-jwt-token"));
    }
}
