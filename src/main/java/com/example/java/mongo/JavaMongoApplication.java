package com.example.java.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class JavaMongoApplication {

  public static void main(String[] args) {
    SpringApplication.run(JavaMongoApplication.class, args);
  }

}
