package com.example.payments.api;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {
    private static final String HEADER = "X-API-KEY";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/actuator") || path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(HEADER);
        String expected = System.getenv().getOrDefault("PAYMENTS_API_KEY", "dev-key");
        if (expected.equals(apiKey)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized");
        }
    }
}


