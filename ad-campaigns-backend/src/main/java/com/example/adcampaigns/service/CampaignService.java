package com.example.adcampaigns.service;

import com.example.adcampaigns.exception.InsufficientFundsException;
import com.example.adcampaigns.model.Campaign;
import com.example.adcampaigns.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Campaign saveCampaign(Campaign campaign) {
        BigDecimal currentBalance = emeraldAccountService.getBalance();

        if (currentBalance.compareTo(campaign.getCampaignFund()) < 0) {
            throw new InsufficientFundsException("Brak wystarczających środków na kampanię");
        }

        // Odejmujemy środki z konta
        emeraldAccountService.deductFunds(campaign.getCampaignFund());

        return campaignRepository.save(campaign);
    }

    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }
}
