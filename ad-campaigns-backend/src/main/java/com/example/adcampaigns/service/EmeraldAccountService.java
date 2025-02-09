package com.example.adcampaigns.service;

import com.example.adcampaigns.model.EmeraldAccount;
import com.example.adcampaigns.repository.EmeraldAccountRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EmeraldAccountService {

    private final EmeraldAccountRepository emeraldAccountRepository;

    @Autowired
    public EmeraldAccountService(EmeraldAccountRepository emeraldAccountRepository) {
        this.emeraldAccountRepository = emeraldAccountRepository;
    }


    @PostConstruct
    public void initializeAccount() {
        Optional<EmeraldAccount> account = emeraldAccountRepository.findById(1L);
        if (account.isEmpty()) {
            EmeraldAccount newAccount = new EmeraldAccount();
            newAccount.setId(1L);
            newAccount.setBalance(new BigDecimal("15000.00"));
            emeraldAccountRepository.save(newAccount);
            System.out.println("✔ Konto zostało utworzone z saldem 15 000.00");
        } else {
            System.out.println("✔ Konto już istnieje, pomijam inicjalizację.");
        }
    }

    // ✅ Pobiera obiekt konta (do użycia w innych metodach)
    public EmeraldAccount getAccount() {
        return emeraldAccountRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Konto nie istnieje!"));
    }

    // ✅ Nowa metoda zwracająca tylko saldo
    public BigDecimal getBalance() {
        return getAccount().getBalance();
    }

    // ✅ Dodawanie środków
    public EmeraldAccount addFunds(BigDecimal amount) {
        EmeraldAccount account = getAccount();
        account.setBalance(account.getBalance().add(amount));
        return emeraldAccountRepository.save(account);
    }

    // ✅ Odejmuje środki (np. gdy tworzysz kampanię)
    public EmeraldAccount deductFunds(BigDecimal amount) {
        EmeraldAccount account = getAccount();
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Brak środków na koncie!");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return emeraldAccountRepository.save(account);
    }
}
