package com.github.olly.workshop.imagethumbnail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.github"})
public class ImagethumbnailApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImagethumbnailApplication.class, args);
    }
}
