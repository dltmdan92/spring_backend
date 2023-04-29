package com.seungmoo.backend.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.seungmoo")
public class   AdapterApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdapterApiApplication.class, args);
    }
}
