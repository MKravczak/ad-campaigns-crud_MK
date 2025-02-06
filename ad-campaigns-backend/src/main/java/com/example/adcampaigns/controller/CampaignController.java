package com.example.adcampaigns.controller;

import com.example.adcampaigns.model.Campaign;
import com.example.adcampaigns.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Arrays;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "http://localhost:4200")
public class CampaignController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        return campaignService.getCampaignById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Campaign createCampaign(@Valid @RequestBody Campaign campaign) {
        return campaignService.saveCampaign(campaign);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id, @Valid @RequestBody Campaign campaign) {
        return campaignService.getCampaignById(id)
                .map(existingCampaign -> {
                    campaign.setId(id);
                    return ResponseEntity.ok(campaignService.saveCampaign(campaign));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        if (!campaignService.getCampaignById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        campaignService.deleteCampaign(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Campaign> partialUpdateCampaign(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        
        return campaignService.getCampaignById(id)
                .map(existingCampaign -> {
                    // Aktualizuj tylko przesłane pola
                    if (updates.containsKey("name")) {
                        existingCampaign.setName((String) updates.get("name"));
                    }
                    if (updates.containsKey("keywords")) {
                        existingCampaign.setKeywords(new HashSet<>((List<String>) updates.get("keywords")));
                    }
                    if (updates.containsKey("bidAmount")) {
                        existingCampaign.setBidAmount(new BigDecimal(updates.get("bidAmount").toString()));
                    }
                    if (updates.containsKey("campaignFund")) {
                        existingCampaign.setCampaignFund(new BigDecimal(updates.get("campaignFund").toString()));
                    }
                    if (updates.containsKey("status")) {
                        existingCampaign.setStatus((Boolean) updates.get("status"));
                    }
                    if (updates.containsKey("town")) {
                        existingCampaign.setTown((String) updates.get("town"));
                    }
                    if (updates.containsKey("radius")) {
                        existingCampaign.setRadius(((Integer) updates.get("radius")));
                    }
                    
                    return ResponseEntity.ok(campaignService.saveCampaign(existingCampaign));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/test")
    public String test() {
        return "API działa!";
    }

    @GetMapping("/towns")
    public List<String> getAvailableTowns() {
        return Arrays.asList(
            "Warszawa", "Kraków", "Łódź", "Wrocław", 
            "Poznań", "Gdańsk", "Szczecin", "Katowice", "Lublin"
        );
    }

    @GetMapping("/keywords")
    public List<String> getPredefineKeywords() {
        return Arrays.asList(
            "promocja",
            "wyprzedaż",
            "nowości",
            "okazje",
            "prezenty",
            "rabaty",
            "sezonowe",
            "limitowane",
            "hurtowe"
        );
    }
} 