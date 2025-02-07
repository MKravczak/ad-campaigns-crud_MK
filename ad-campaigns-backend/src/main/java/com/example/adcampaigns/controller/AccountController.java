package com.example.adcampaigns.controller;

import com.example.adcampaigns.service.EmeraldAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final EmeraldAccountService emeraldAccountService;

    @Autowired
    public AccountController(EmeraldAccountService emeraldAccountService) {
        this.emeraldAccountService = emeraldAccountService;
    }

    @GetMapping("/balance")
    public BigDecimal getBalance() {
        return emeraldAccountService.getBalance();
    }

    @PostMapping("/addFunds")
    public void addFunds(@RequestParam BigDecimal amount) {
        emeraldAccountService.addFunds(amount);
    }
}
