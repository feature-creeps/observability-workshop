package com.github.olly.workshop.imageorchestrator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class ImageorchestratorApplicationTests {

    @Test
    public void contextLoads() {
    }
}
