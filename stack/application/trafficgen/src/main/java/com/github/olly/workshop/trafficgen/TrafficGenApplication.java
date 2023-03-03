package com.github.olly.workshop.trafficgen;

import com.github.olly.workshop.springevents.SpringBootEventsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import com.github.olly.workshop.trafficgen.service.*;

@SpringBootApplication
@EnableFeignClients(basePackages = { "com.github" })
@Import(SpringBootEventsConfiguration.class)
public class TrafficGenApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TrafficGenApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
