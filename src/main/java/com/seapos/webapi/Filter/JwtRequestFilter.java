package com.seapos.webapi.Filter;

import com.seapos.webapi.Utility.JWTToken;
import com.seapos.webapi.models.AuthResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JWTToken jwtUtil;
    // URLs that should bypass JWT filter
    private static final String[] WHITELIST = {
            "/webapi/auth/login",
            "/swagger-ui",
            "/v3/api-docs"
    };

    private boolean isWhitelisted(String uri) {
        for (String path : WHITELIST) {
            if (uri.startsWith(path)) return true;
        }
        return false;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        // 1. Allow Swagger + Login// BYPASS JWT for public URLs
        if (isWhitelisted(uri)) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Read headers
        final String authHeader = request.getHeader("Authorization");
        final String headerUsername = request.getHeader("username");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
            return;
        }

        if (headerUsername == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
            return;
        }

        // 3. Extract JWT & username
        String jwt = authHeader.substring(7);
        String usernameFromToken = jwtUtil.extractUsername(jwt);

        if (usernameFromToken == null || !usernameFromToken.equals(headerUsername)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
            return;
        }

        // 4. Validate token
        if (!jwtUtil.isTokenValid(jwt, usernameFromToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
            return;
        }

        // 5. Continue
        chain.doFilter(request, response);
    }
}