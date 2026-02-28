package com.example.UberProject_AuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {
        "com.example.UberProject_EntityService.models"
})
@EnableJpaRepositories(basePackages = {
        "com.example.UberProject_AuthService.repositries"
})
public class UberProjectAuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UberProjectAuthServiceApplication.class, args);
    }
}