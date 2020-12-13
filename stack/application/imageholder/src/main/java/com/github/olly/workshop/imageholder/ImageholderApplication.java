package com.github.olly.workshop.imageholder;

import com.github.olly.workshop.springevents.SpringBootEventsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
@Import(SpringBootEventsConfiguration.class)
public class ImageholderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImageholderApplication.class, args);
    }
}
