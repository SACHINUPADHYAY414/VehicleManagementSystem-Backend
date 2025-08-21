package com.project.dvManagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status; 
    
    public void setModel(String model) { this.model = model; }
    public void setPrice(Double price) { this.price = price; }
    public void setStatus(VehicleStatus status) { this.status = status; }
    public void setDealer(Dealer dealer) { this.dealer = dealer; }

    public String getModel() { return model; }
    public Double getPrice() { return price; } 
    public VehicleStatus getStatus() { return status; }
    public Dealer getDealer() { return dealer; }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

