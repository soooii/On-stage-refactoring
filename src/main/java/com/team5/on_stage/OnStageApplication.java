package com.team5.on_stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnStageApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnStageApplication.class, args);
	}

}
