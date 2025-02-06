package com.example.adcampaigns.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class EmeraldAccount {
    @Id
    private Long id;
    private BigDecimal balance;
} 