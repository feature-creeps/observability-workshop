package com.github.olly.workshop.imageorchestrator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ImageorchestratorApplicationTests {

    @Test
    public void contextLoads() {
    }
}
