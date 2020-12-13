package com.github.olly.workshop.imageflip;

import com.github.olly.workshop.springevents.SpringBootEventsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SpringBootEventsConfiguration.class)
public class ImageflipApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImageflipApplication.class, args);
    }
}
