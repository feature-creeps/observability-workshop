package com.github.olly.workshop.imageholder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;

@DataMongoTest
@Import({MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class})
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class ImageholderApplicationTests {

    @Test
    public void contextLoads() {
    }
}
