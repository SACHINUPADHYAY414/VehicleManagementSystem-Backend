package com.vehiclehub.DTO;

import com.vehiclehub.entity.SubscriptionType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DealerRequestDto {

    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Email is required")
    private String email;   
    @NotNull(message = "mobile no is required")
    private String mobileNumber;

    private SubscriptionType subscriptionType;

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }
}
