package com.veeteq.finance.bankdocument;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.veeteq.finance.bankdocument.model")
@EnableJpaRepositories(basePackages = "com.veeteq.finance.bankdocument.repository")
public class BankDocumentApp {
	
	public static void main(String[] args) {
		SpringApplication.run(BankDocumentApp.class, args);
	}

}
