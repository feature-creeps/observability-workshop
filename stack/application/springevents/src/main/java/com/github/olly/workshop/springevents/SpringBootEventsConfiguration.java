package com.github.olly.workshop.springevents;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.github.olly")
@EntityScan("com.github.olly")
public class SpringBootEventsConfiguration {
}
