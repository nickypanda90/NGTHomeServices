package com.txstate.edu.homeServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomeServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeServicesApplication.class, args);
	}

}
