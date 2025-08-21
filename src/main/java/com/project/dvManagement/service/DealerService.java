package com.project.dvManagement.service;

import com.project.dvManagement.entity.Dealer;
import com.project.dvManagement.repository.DealerRepository;
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

    public List<Dealer> getAllDealers() {
        return dealerRepository.findAll();
    }
}
