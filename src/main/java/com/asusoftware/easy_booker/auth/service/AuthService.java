package com.asusoftware.easy_booker.auth.service;


import com.asusoftware.easy_booker.auth.dto.AuthRequest;
import com.asusoftware.easy_booker.auth.dto.RegisterRequest;
import com.asusoftware.easy_booker.config.JwtUtil;
import com.asusoftware.easy_booker.user.repository.UserRepository;
import com.asusoftware.easy_booker.user.model.User;
import com.asusoftware.easy_booker.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;


    public void register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        // Atribuie rolul implicit ROLE_USER
        user.setRole(UserRole.ROLE_USER);

        userRepository.save(user);
    }

    public String login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(user.getEmail());
    }
}
