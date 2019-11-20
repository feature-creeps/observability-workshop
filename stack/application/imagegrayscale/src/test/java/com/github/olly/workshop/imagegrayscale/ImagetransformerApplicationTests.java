package com.github.olly.workshop.imagegrayscale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"honeycomb.beeline.enabled=false"})
public class ImagetransformerApplicationTests {

    @Test
    public void contextLoads() {
    }

}

