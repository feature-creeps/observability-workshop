package com.github.olly.workshop.trafficgen.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransformationTrafficService implements TriggeredService {
    private static final long S_IN_MS = 1000L;

    public static String SERVICE_KEY = "tranformation-traffic-service";

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationTrafficService.class);

    @Autowired
    private RandomTransformationService randomTransformationService;

    @Override
    public Optional<String> getKey() {
        return Optional.of(SERVICE_KEY);
    };

    @Override
    public Trigger constructTrigger(long transformationsPerSecond) {
        LOGGER.info("Constructing trigger with transformationsPerSecond " + transformationsPerSecond);
        if (transformationsPerSecond > 0) {
            return new PeriodicTrigger(S_IN_MS / transformationsPerSecond);
        }
        PeriodicTrigger never = new PeriodicTrigger(Long.MAX_VALUE, TimeUnit.HOURS);
        never.setInitialDelay(Long.MAX_VALUE);
        return never;
    }

    @Override
    public void run() {
        randomTransformationService.sendRandomRequest();
    }
}