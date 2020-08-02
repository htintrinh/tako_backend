package com.hungtin.tako.takobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@EnableAsync
public class WikiBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(WikiBackendApplication.class, args);
  }
}
