package com.example.springboot.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/google")

    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> body) {
        System.out.println("🚀 Entered /api/auth/google endpoint");
        String idTokenString = body.get("token");
        System.out.println("✅ Received token: " + (idTokenString != null));

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(
                        "583541403083-ip677njslglhptvtpq2fshqpv66g3j7q.apps.googleusercontent.com"

                ))
                .build();

        try {
            System.out.println("⏳ Starting token verification");

            // For testing, skip Google validation if token = "dummy"
            if ("dummy".equals(idTokenString)) {
                System.out.println("✅ Skipping real verification (dummy token)");
                String jwt = jwtService.generateToken("test@example.com");
                return ResponseEntity.ok(Map.of("access_token", jwt));
            }

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                String email = idToken.getPayload().getEmail();
                System.out.println("✅ Verified Google token for: " + email);
                String jwt = jwtService.generateToken(email);
                return ResponseEntity.ok(Map.of("access_token", jwt));
            } else {
                System.out.println("❌ Invalid or mismatched ID token");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid ID token");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Verification error: " + e.getMessage());
        }
    }
}

