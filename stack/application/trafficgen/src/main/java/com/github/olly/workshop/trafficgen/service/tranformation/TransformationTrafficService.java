package com.github.olly.workshop.trafficgen.service.tranformation;

import com.github.olly.workshop.trafficgen.service.TriggeredService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TransformationTrafficService implements TriggeredService {
    private static final long S_IN_MS = 1000L;

    public static final String SERVICE_KEY = "tranformation-traffic-service";

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationTrafficService.class);

    private final TransformationService randomTransformationService;

    @Override
    public Optional<String> getKey() {
        return Optional.of(SERVICE_KEY);
    }

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