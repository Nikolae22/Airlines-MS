package com.airlanecoreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AirlaneCoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirlaneCoreServiceApplication.class, args);
    }

}
