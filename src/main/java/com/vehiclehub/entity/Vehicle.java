package com.vehiclehub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private Double price;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "dealer_id")
    @JsonBackReference
    private Dealer dealer;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    
    private String imageUrl;
}
