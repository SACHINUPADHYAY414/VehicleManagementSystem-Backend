package com.vehiclehub.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequestDto {

    @NotBlank(message = "Model is required")
    private String model;

    @Positive(message = "Price must be positive")
    private double price;

    @NotNull(message = "Year (date) is required")
    private Integer year;

    @NotBlank(message = "Status is required (AVAILABLE or SOLD)")
    private String status;

    @NotNull(message = "Dealer ID is required")
    private Long dealerId;
}
