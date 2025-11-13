package com.rentease.service;

import com.rentease.model.User;
import com.rentease.payload.LoginRequest;
import com.rentease.payload.RegisterRequest;
import com.rentease.repository.UserRepository;
import com.rentease.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register user
    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setUsername(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<String> roles = request.getRoles();
        if (roles == null || roles.isEmpty()) {
            roles = new HashSet<>();
            roles.add("USER");
        }
        user.setRoles(roles);

        userRepository.save(user);

        return jwtUtil.generateToken(user.getEmail(), user.getRoles());
    }

    // Login user
    public String login(LoginRequest request) {
        User user = userRepository.findByEmailOrUsername(request.getEmail(), request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRoles());
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Get roles for JWT
    public Set<String> getRolesByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getRoles)
                .orElse(Collections.emptySet());
    }

    // Update user
    public boolean updateUserById(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPhone(updatedUser.getPhone());
            user.setEmail(updatedUser.getEmail());
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    // Delete user
    public boolean deleteUserById(String id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}
