package com.hospital.apigateway.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // -------------------------------
        // 1. Public endpoints
        // -------------------------------

        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/actuator")) {
            filterChain.doFilter(request, response);
            return;
        }

        // -------------------------------
        // 2. Check Authorization header
        // -------------------------------

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"error\":\"Authorization token is missing\"}"
            );

            return;
        }

        // -------------------------------
        // 3. Extract JWT
        // -------------------------------

        String token =
                authorizationHeader.substring(7);

        // -------------------------------
        // 4. Validate JWT
        // -------------------------------

        if (!jwtUtil.validateToken(token)) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"error\":\"Invalid or expired token\"}"
            );

            return;
        }

        // -------------------------------
        // 5. Extract role
        // -------------------------------

        String role = jwtUtil.extractRole(token);

        if (role == null) {

            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN
            );

            response.setContentType("application/json");

            response.getWriter().write(
                    "{\"error\":\"Role not found in token\"}"
            );

            return;
        }

        role = role.toUpperCase();

        // -------------------------------
        // 6. ADMIN-only Patient changes
        // -------------------------------

        if (path.startsWith("/patients") &&
                (method.equals("POST") ||
                        method.equals("PUT"))) {

            if (!role.equals("ADMIN")) {

                response.setStatus(
                        HttpServletResponse.SC_FORBIDDEN
                );

                response.setContentType("application/json");

                response.getWriter().write(
                        "{\"error\":\"Only ADMIN can create or update patients\"}"
                );

                return;
            }
        }

        // -------------------------------
        // 7. ADMIN-only Doctor changes
        // -------------------------------

        if (path.startsWith("/doctors") &&
                (method.equals("POST") ||
                        method.equals("PUT"))) {

            if (!role.equals("ADMIN")) {

                response.setStatus(
                        HttpServletResponse.SC_FORBIDDEN
                );

                response.setContentType("application/json");

                response.getWriter().write(
                        "{\"error\":\"Only ADMIN can create or update doctors\"}"
                );

                return;
            }
        }

        // -------------------------------
        // 8. Continue to microservice
        // -------------------------------

        filterChain.doFilter(request, response);
    }
}