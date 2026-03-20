package com.shiv.bookstore.orders;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class OrderServiceApplication {



    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }


}
