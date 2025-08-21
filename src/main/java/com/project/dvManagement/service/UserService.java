package com.project.dvManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.dvManagement.DTO.LoginRequest;
import com.project.dvManagement.DTO.RegisterRequest;
import com.project.dvManagement.config.JwtTokenProvider;
import com.project.dvManagement.entity.User;
import com.project.dvManagement.repository.UserRepository;

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
    	if (userRepository.findByEmail(request.getEmail()).isPresent()) {
    	    throw new RuntimeException("Email already exists");
    	}

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public String authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenProvider.generateToken(authentication);
        } catch (Exception ex) {
            // Log here
            throw ex; // or wrap and throw a custom exception
        }
    }

}
