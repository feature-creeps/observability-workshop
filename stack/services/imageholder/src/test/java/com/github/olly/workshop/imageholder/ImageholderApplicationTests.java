package com.github.olly.workshop.imageholder;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageholderApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Configuration
    public static class MongoConfig extends AbstractMongoConfiguration {

        @Override
        protected String getDatabaseName() {
            return "test";
        }

        @Override
        public Mongo mongo() throws Exception {
            return new Fongo("mongo-test").getMongo();
        }
    }
}
