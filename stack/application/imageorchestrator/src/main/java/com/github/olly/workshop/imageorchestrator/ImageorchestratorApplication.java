package com.github.olly.workshop.imageorchestrator;

import com.github.olly.workshop.springevents.SpringBootEventsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.github"})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@Import(SpringBootEventsConfiguration.class)
public class ImageorchestratorApplication {

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext context = SpringApplication.run(ImageorchestratorApplication.class, args);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            t.printStackTrace();
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
