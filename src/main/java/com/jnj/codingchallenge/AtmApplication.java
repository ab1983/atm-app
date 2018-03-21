package com.jnj.codingchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;

import com.jnj.codingchallenge.service.AtmService;

@SpringBootApplication
@Profile("!test")
public class AtmApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AtmApplication.class, args);
		context.getBean(AtmService.class).populateDatabase();
	}
}
