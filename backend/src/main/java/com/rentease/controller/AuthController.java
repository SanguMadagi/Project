package com.rentease.controller;

import com.rentease.payload.LoginRequest;
import com.rentease.payload.RegisterRequest;
import com.rentease.payload.JwtResponse;
import com.rentease.service.UserService;
import com.rentease.config.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtils;

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        String token = userService.register(request); // handles user creation
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // Login endpoint using Spring Security AuthenticationManager
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Set<String> roles = userService.getRolesByEmail(userDetails.getUsername());

            String jwt = jwtUtils.generateToken(userDetails.getUsername(), roles);
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (Exception e) {
            return ResponseEntity
                    .status(401)
                    .body("Error: Invalid email or password");
        }
    }
}
