package com.rentease.payload;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Set<String> roles; // optional
}
