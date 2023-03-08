package com.github.olly.workshop.trafficgen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

@Service
public class TransformationTrafficGenService {

    @Autowired
    private RandomTransformationService randomTransformationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationTrafficGenService.class);
    private static final long S_IN_MS = 1000L;

    private int transformationsPerSecond = 0;

    public void setTransformationsPerSecond(int transformationsPerSecond) {
        this.transformationsPerSecond = transformationsPerSecond;
    }

    @Async
    @Scheduled(fixedRate = S_IN_MS)
    public void generateTraffic() {
        for (int i = 0; i < transformationsPerSecond; i++) {
            randomTransformationService.sendRandomRequest();
            try {
                wait(S_IN_MS / transformationsPerSecond);
            } catch (InterruptedException e) {
                LOGGER.warn("Interrupted waiting", e);
            }
        }
    }
}