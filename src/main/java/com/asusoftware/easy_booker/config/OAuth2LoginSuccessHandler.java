package com.asusoftware.easy_booker.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Logica personalizată pentru autentificarea reușită
        // De exemplu, generarea unui token JWT și trimiterea acestuia în răspuns
        response.sendRedirect("/"); // Redirecționează utilizatorul către pagina principală după autentificare
    }
}