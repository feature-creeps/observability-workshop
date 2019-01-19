package com.github.olly.workshop.imageholder;

import com.github.olly.workshop.imageholder.service.ImageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ImageholderApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImageholderApplication.class, args);

        context.getBean(ImageService.class).initImagesInDatabaseMetric();
    }
}
