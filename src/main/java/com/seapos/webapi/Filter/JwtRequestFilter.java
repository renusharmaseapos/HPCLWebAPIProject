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
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Value("${spring.application.Env}")
    private String Env;  // minutes
    @Value("${spring.application.ApplicationName}")
    private  String ApplicationName;

    public static String Appname;
    @Autowired
    private JWTToken jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        Appname=ApplicationName;
        final String authHeader = request.getHeader("Authorization");
        final String headerUsername = request.getHeader("username");

        String username = null;
        String jwt = null;
        if(Objects.equals(Env, "Dev"))
        // if(request.getRequestURL().toString().toUpperCase().contains("LOGIN"))
        {
            chain.doFilter(request, response);
            return;
        }
       else if(request.getRequestURL().toString().toUpperCase().contains("LOGIN"))
        {
            chain.doFilter(request, response);
            return;
        }
        else if(headerUsername == null)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid Token");
            return;
        }
        else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid Token");
                return;
            } else {
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    jwt = authHeader.substring(7);
                    username = jwtUtil.extractUsername(jwt);
                }
                if (username != null ) {
                    if(!headerUsername.equals(username)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid Token");
                        return;
                    }
                    else
                    if (!jwtUtil.isTokenValid(jwt, username)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid Token");
                        return;
                    }
                }
                else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid Token");
                    return;
                }


                chain.doFilter(request, response);
            }
        }
    }
}