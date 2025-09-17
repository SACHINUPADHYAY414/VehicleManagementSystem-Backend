package com.vehiclehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehiclehub.DTO.JwtResponse;
import com.vehiclehub.DTO.LoginRequest;
import com.vehiclehub.DTO.LoginResponse;
import com.vehiclehub.DTO.RegisterRequest;
import com.vehiclehub.entity.User;
import com.vehiclehub.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = userService.authenticate(request);

        User user = userService.getUserByEmailOrName(request.getEmail(), request.getName());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setMessage("Login successful");
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setGender(user.getGender());
        response.setTitle(user.getTitle());
        response.setDob(user.getDob() != null ? user.getDob() : null);
        response.setMobileNumber(user.getMobileNumber());
        response.setCountry(user.getCountry());
        response.setCity(user.getCity());
        response.setState(user.getState());
        response.setPinCode(user.getPinCode());

        return ResponseEntity.ok(response);
    }



    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
}

