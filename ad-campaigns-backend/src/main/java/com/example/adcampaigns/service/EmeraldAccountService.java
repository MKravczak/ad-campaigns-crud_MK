package com.example.adcampaigns.service;

import com.example.adcampaigns.model.EmeraldAccount;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class EmeraldAccountService {
    public EmeraldAccount getAccount() {
        // Mock implementation
        EmeraldAccount account = new EmeraldAccount();
        account.setId(1L);
        account.setBalance(new BigDecimal("10000.00"));
        return account;
    }

    public void saveAccount(EmeraldAccount account) {
        // Mock implementation
    }
} 