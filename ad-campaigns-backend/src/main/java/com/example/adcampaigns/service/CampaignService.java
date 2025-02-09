package com.example.adcampaigns.service;

import com.example.adcampaigns.exception.InsufficientFundsException;
import com.example.adcampaigns.model.Campaign;
import com.example.adcampaigns.repository.CampaignRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final EmeraldAccountService emeraldAccountService;

    @Autowired
    public CampaignService(CampaignRepository campaignRepository, EmeraldAccountService emeraldAccountService) {
        this.campaignRepository = campaignRepository;
        this.emeraldAccountService = emeraldAccountService;
    }

    /**
     * Pobiera wszystkie kampanie z bazy danych
     */
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    /**
     * Pobiera kampanię po ID
     */
    public Optional<Campaign> getCampaignById(Long id) {
        return campaignRepository.findById(id);
    }

    /**
     * Zapisuje nową kampanię do bazy danych (po uprzednim sprawdzeniu środków)
     */
    public Campaign saveCampaign(Campaign campaign) {
        BigDecimal currentBalance = emeraldAccountService.getBalance();

        if (currentBalance.compareTo(campaign.getCampaignFund()) < 0) {
            throw new InsufficientFundsException("Brak wystarczających środków na kampanię");
        }

        // Odejmujemy środki z konta użytkownika
        emeraldAccountService.deductFunds(campaign.getCampaignFund());

        return campaignRepository.save(campaign);
    }

    /**
     * Usuwa kampanię na podstawie ID
     */
    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }

    /**
     * Metoda wykonywana po starcie aplikacji - dodaje domyślną kampanię, jeśli jeszcze jej nie ma.
     */
    @PostConstruct
    public void initializeDefaultCampaign() {
        if (campaignRepository.count() == 0) {
            Campaign defaultCampaign = new Campaign();
            defaultCampaign.setName("Kampania Reklamowa");
            defaultCampaign.setKeywords(new HashSet<>(Set.of("rabat", "promocja")));
            defaultCampaign.setBidAmount(BigDecimal.valueOf(10.50));
            defaultCampaign.setCampaignFund(BigDecimal.valueOf(1000.00));
            defaultCampaign.setStatus(true);
            defaultCampaign.setTown("Warszawa");
            defaultCampaign.setRadius(10);

            saveCampaign(defaultCampaign);
        }
    }
}
