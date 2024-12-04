package com.project.Clinical_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan("com.project.Clinical_System.entity")
public class ClinicalSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClinicalSystemApplication.class, args);
	}

}
