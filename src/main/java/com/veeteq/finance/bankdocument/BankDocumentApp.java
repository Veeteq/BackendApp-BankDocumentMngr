package com.veeteq.finance.bankdocument;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EntityScan(basePackages = "com.veeteq.finance.bankdocument.model")
@EnableJpaRepositories(basePackages = "com.veeteq.finance.bankdocument.repository")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.veeteq.finance.bankdocument.integration")
@OpenAPIDefinition(info = @Info(title = "Bank Document Manager API",
                                version = "1.0",
                                description = "Bank Document Manager Information",
                                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html")))
public class BankDocumentApp {

	public static void main(String[] args) {
		SpringApplication.run(BankDocumentApp.class, args);
	}

}
