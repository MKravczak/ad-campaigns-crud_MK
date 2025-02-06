package com.example.adcampaigns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "campaigns")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nazwa kampanii jest wymagana")
    private String name;

    @ElementCollection
    @CollectionTable(name = "campaign_keywords", joinColumns = @JoinColumn(name = "campaign_id"))
    @Column(name = "keyword")
    private Set<String> keywords;

    @NotNull(message = "Kwota stawki jest wymagana")
    @Min(value = 0, message = "Stawka musi być większa od 0")
    private BigDecimal bidAmount;

    @NotNull(message = "Budżet kampanii jest wymagany")
    @Min(value = 0, message = "Budżet musi być większy od 0")
    private BigDecimal campaignFund;

    @NotNull(message = "Status jest wymagany")
    private Boolean status;

    private String town;

    @NotNull(message = "Promień jest wymagany")
    @Min(value = 0, message = "Promień musi być większy od 0")
    private Integer radius;
} 