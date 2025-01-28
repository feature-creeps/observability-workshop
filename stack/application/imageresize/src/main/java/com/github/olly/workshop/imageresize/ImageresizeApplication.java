package com.github.olly.workshop.imageresize;

import com.github.olly.workshop.springevents.SpringBootEventsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SpringBootEventsConfiguration.class)
public class ImageresizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageresizeApplication.class, args);
    }
}
