package com.github.olly.workshop.imageholder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ImageholderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImageholderApplication.class, args);
    }
}
