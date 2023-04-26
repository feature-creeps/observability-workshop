package com.github.olly.workshop.imagethumbnail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@DataMongoTest
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class ImagethumbnailApplicationTests {

    @Test
    public void contextLoads() {
    }
}
