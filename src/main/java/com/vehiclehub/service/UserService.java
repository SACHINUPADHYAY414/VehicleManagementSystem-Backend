package com.vehiclehub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vehiclehub.DTO.LoginRequest;
import com.vehiclehub.DTO.RegisterRequest;
import com.vehiclehub.config.JwtTokenProvider;
import com.vehiclehub.entity.User;
import com.vehiclehub.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void registerUser(RegisterRequest request) {
        // Optional pre-check; this is not race condition safe but improves UX
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setGender(request.getGender());
        user.setTitle(request.getTitle());
        user.setDob(request.getDob());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setCountry(request.getCountry());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setPinCode(request.getPinCode());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // This usually means a unique constraint violation, e.g. email already exists
            throw new RuntimeException("Email already exists");
        }
    }

    public String authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.generateToken(authentication);
        } catch (Exception ex) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    // Method to fetch user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
