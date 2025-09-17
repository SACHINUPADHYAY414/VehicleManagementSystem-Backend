package com.vehiclehub.DTO;

import com.vehiclehub.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public class PaymentRequestDto {

	 @NotNull(message = "User ID is required")
	    private Long userId;
	 
    @NotNull(message = "Vehicle ID is required")
    private Long vehicleId;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod method;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
}
