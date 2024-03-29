package com.github.olly.workshop.trafficgen.service.tranformation;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import org.springframework.stereotype.Service;

import com.github.olly.workshop.trafficgen.service.SpringEventTriggeredServiceSchedulerBase;

@Service
public class TransformationTrafficServiceScheduler
        extends SpringEventTriggeredServiceSchedulerBase<TransformationTrafficService> {

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private TransformationTrafficService services;

    @Override
    protected Set<TransformationTrafficService> getServices() {
        return Set.of(services);
    }

    @Override
    protected TaskScheduler getScheduler() {
        return scheduler;
    }

}