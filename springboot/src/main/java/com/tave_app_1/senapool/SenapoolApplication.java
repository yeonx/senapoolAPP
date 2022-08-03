package com.tave_app_1.senapool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SenapoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenapoolApplication.class, args);
	}

}
