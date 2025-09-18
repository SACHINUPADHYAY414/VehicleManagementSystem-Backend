package com.vehiclehub.DTO;

import com.vehiclehub.entity.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponseDto {
    private Long id;
    private String model;
    private Double price;
    private Integer year;
    private VehicleStatus status;
    private Long dealerId;
    private String imageUrl;

}

