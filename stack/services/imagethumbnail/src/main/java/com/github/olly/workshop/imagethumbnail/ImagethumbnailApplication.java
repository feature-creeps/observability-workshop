package com.github.olly.workshop.imagethumbnail;

import com.github.olly.workshop.imagethumbnail.service.ImageService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.github"})
public class ImagethumbnailApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ImagethumbnailApplication.class, args);
    }
}
