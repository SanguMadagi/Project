package com.rentease.controller;

import com.rentease.model.User;
import com.rentease.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get current user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can delete
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return userService.deleteUserById(id)
                ? ResponseEntity.ok("User deleted successfully")
                : ResponseEntity.status(404).body("User not found");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can update
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return userService.updateUserById(id, updatedUser)
                ? ResponseEntity.ok("User updated successfully")
                : ResponseEntity.status(404).body("User not found");
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }
}