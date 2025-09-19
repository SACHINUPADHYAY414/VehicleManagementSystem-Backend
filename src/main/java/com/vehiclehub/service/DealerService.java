package com.vehiclehub.service;

import com.vehiclehub.entity.Dealer;
import com.vehiclehub.repository.DealerRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealerService {
    @Autowired
    private DealerRepository dealerRepository;

    public Dealer saveDealer(Dealer dealer) {
        return dealerRepository.save(dealer);
    }

    public Dealer getDealerById(Long id) {
        return dealerRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<Dealer> getAllDealers() {
        return dealerRepository.findAll();
    }
}
