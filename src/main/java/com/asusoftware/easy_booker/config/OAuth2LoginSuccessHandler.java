package com.asusoftware.easy_booker.config;


import com.asusoftware.easy_booker.user.repository.UserRepository;
import com.asusoftware.easy_booker.user.repository.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Optional;


@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");

        // Verifică dacă utilizatorul există în baza de date
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            // Creează un nou utilizator
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(email);
            newUser.setPassword(""); // OAuth2 utilizatorii nu au parolă în aplicație
            userRepository.save(newUser);
        }

        // Generează un token JWT
        String token = jwtUtil.generateToken(email);

        // Redirecționează la endpoint-ul de succes cu token-ul JWT
        response.sendRedirect("/api/auth/oauth2/success?token=" + token);
    }
}