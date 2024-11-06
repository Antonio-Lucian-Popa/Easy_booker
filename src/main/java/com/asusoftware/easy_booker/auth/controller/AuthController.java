package com.asusoftware.easy_booker.auth.controller;


import com.asusoftware.easy_booker.auth.dto.AuthRequest;
import com.asusoftware.easy_booker.auth.dto.AuthResponse;
import com.asusoftware.easy_booker.auth.dto.RegisterRequest;
import com.asusoftware.easy_booker.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint pentru înregistrare manuală
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    // Endpoint pentru autentificare manuală (username și parolă)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        return ResponseEntity.ok(authResponse);
    }

    // Endpoint pentru succesul autentificării OAuth2 (Google/Facebook)
    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> oauth2LoginSuccess(@RequestParam("token") String token) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        return ResponseEntity.ok(authResponse);
    }
}