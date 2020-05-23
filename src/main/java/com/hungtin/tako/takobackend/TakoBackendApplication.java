package com.hungtin.tako.takobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TakoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TakoBackendApplication.class, args);
	}

}
