package com.github.olly.workshop.trafficgen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.scheduling.annotation.Async;

@Service
public class TransformationTrafficGenService {

    @Autowired
    private RandomTransformationService randomTransformationService;

    private int transformationsPerSecond = 0;

    public void setTransformationsPerSecond(int transformationsPerSecond) {
        this.transformationsPerSecond = transformationsPerSecond;
    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void generateTraffic() {
        for (int i = 0; i < transformationsPerSecond; i++) {
            randomTransformationService.sendRandomRequest();
        }
    }
}