package com.example.adcampaigns.service;

import com.example.adcampaigns.model.Campaign;
import com.example.adcampaigns.model.EmeraldAccount;
import com.example.adcampaigns.repository.CampaignRepository;
import com.example.adcampaigns.exception.InsufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final EmeraldAccountService emeraldAccountService;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository, EmeraldAccountService emeraldAccountService) {
        this.campaignRepository = campaignRepository;
        this.emeraldAccountService = emeraldAccountService;
    }

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Optional<Campaign> getCampaignById(Long id) {
        return campaignRepository.findById(id);
    }

    @Transactional
    public Campaign saveCampaign(Campaign campaign) {
        // Sprawdź dostępne środki
        EmeraldAccount account = emeraldAccountService.getAccount();
        if (account.getBalance().compareTo(campaign.getCampaignFund()) < 0) {
            throw new InsufficientFundsException("Niewystarczające środki na koncie");
        }
        
        // Odejmij środki
        account.setBalance(account.getBalance().subtract(campaign.getCampaignFund()));
        emeraldAccountService.saveAccount(account);
        
        return campaignRepository.save(campaign);
    }

    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }
} 