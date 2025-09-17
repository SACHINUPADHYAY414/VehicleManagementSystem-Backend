package com.vehiclehub.controller;

import com.vehiclehub.DTO.DealerRequestDto;
import com.vehiclehub.entity.Dealer;
import com.vehiclehub.entity.Vehicle;
import com.vehiclehub.service.DealerService;
import com.vehiclehub.service.VehicleService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealers")
public class DealerController {

    @Autowired
    private DealerService dealerService;

    @PostMapping
    public Dealer createDealer(@Valid @RequestBody DealerRequestDto dto) {
        Dealer dealer = new Dealer();
        dealer.setName(dto.getName());
        dealer.setEmail(dto.getEmail());
        dealer.setMobileNumber(dto.getMobileNumber());
        dealer.setSubscriptionType(dto.getSubscriptionType());

        return dealerService.saveDealer(dealer);
    }

    @GetMapping
    public List<Dealer> getAllDealers() {
        return dealerService.getAllDealers();
    }

}
