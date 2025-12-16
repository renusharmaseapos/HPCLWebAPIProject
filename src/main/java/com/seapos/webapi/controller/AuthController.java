package com.seapos.webapi.controller;

import com.seapos.webapi.Utility.JWTToken;
import com.seapos.webapi.models.AuthRequest;
import com.seapos.webapi.models.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("webapi/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
   // private AuthenticationManager authenticationManager;
    @Autowired private JWTToken jwtUtil;
   // @Autowired private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        String username = request.getUsername();
        String ipAddress = getClientIp(httpRequest);
        logger.info("LOGIN_ATTEMPT | username={} | ip={}", username, ipAddress);
        String token ="";
        if ("admin".equals(request.getUsername()) && "admin".equals(request.getPassword())) {
            token = jwtUtil.generateToken(request.getUsername());
            //Login success log
            logger.info("LOGIN_SUCCESS | username={} | ip={}", username, ipAddress);
            return ResponseEntity.ok(new AuthResponse(token));
          }
        // Login failed log
        logger.warn("LOGIN_FAILED | username={} | ip={}", username, ipAddress);

        return ResponseEntity.status(401)
                .body(new AuthResponse("Invalid credentials"));

    }
    // ✔ Correct way to get client IP (proxy-safe)
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
