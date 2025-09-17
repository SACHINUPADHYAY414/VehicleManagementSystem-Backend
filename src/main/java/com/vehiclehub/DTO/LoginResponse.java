package com.vehiclehub.DTO;

import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String message;
    private String username;
    private String name;
    private String email;
    private String gender;
    private String title;
    private LocalDate dob;
    private String mobileNumber;
    private String country;
    private String city;
    private String state;
    private String pinCode;
}
