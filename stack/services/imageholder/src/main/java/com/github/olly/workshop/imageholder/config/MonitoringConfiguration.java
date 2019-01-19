package com.github.olly.workshop.imageholder.config;

import com.github.olly.workshop.imageholder.adapter.RequestCounterInterceptor;
import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

@Configuration
@ConditionalOnWebApplication
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
class MonitoringConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringConfiguration.class);

    @PostConstruct
    public void init() {
        LOGGER.debug("Initializing default prometheus metrics to export.");
        DefaultExports.initialize();
    }

    @Autowired
    private RequestCounterInterceptor requestCounterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOGGER.debug("Initializing request counter interceptor.");
        registry.addInterceptor(requestCounterInterceptor);
    }
}