package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserPrincipal {
    private String userId;
    private String role;
    private List<String> permissions;
}
