package com.example.adcampaigns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;




@SpringBootApplication
@EntityScan("com.example.adcampaigns.model")
@EnableJpaRepositories("com.example.adcampaigns.repository")
public class AdCampaignsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdCampaignsBackendApplication.class, args);
	}

}
