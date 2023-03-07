package com.github.olly.workshop.imagethumbnail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ImagethumbnailApplicationTests {

    @Test
    public void contextLoads() {
    }
}
