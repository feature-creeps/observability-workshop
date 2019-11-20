package com.github.olly.workshop.imagerotator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ImagerotatorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImagerotatorApplication.class, args);
    }
}
