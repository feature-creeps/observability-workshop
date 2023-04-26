package com.github.olly.workshop.trafficgen.service.upload;

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
public class UploadTrafficService implements TriggeredService {
    private static final long S_IN_MS = 1000L;

    public static final String SERVICE_KEY = "upload-traffic-service";

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadTrafficService.class);

    private final UploadService uploadService;

    @Override
    public Optional<String> getKey() {
        return Optional.of(SERVICE_KEY);
    }

    @Override
    public Trigger constructTrigger(long transformationsPerSecond) {
        LOGGER.info("Constructing trigger with uploadsPerSecond " + transformationsPerSecond);
        if (transformationsPerSecond > 0) {
            return new PeriodicTrigger(S_IN_MS / transformationsPerSecond);
        }
        PeriodicTrigger onceNow = new PeriodicTrigger(Long.MAX_VALUE, TimeUnit.HOURS);
        return onceNow;
    }

    @Override
    public void run() {
        uploadService.uploadAllImages();
    }
}