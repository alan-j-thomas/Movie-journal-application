package com.example.AuthenticationService.controller;

import com.example.AuthenticationService.entity.AuthenticationResponse;
import com.example.AuthenticationService.entity.UserCredentials;
import com.example.AuthenticationService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserCredentials user){
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserCredentials user){
        return ResponseEntity.ok(authService.authenticate(user));
    }
}
