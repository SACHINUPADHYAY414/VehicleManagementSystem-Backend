package com.vehiclehub.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;
    private String title;
    private LocalDate dob;

    @Column(unique = true)
    private String email;

    private String mobileNumber;
    private String country;
    private String city;
    private String state;
    private String pinCode;

    private String password;
}
