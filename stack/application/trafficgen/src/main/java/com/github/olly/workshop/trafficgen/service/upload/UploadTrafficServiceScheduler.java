package com.github.olly.workshop.trafficgen.service.upload;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import org.springframework.stereotype.Service;

import com.github.olly.workshop.trafficgen.service.SpringEventTriggeredServiceSchedulerBase;

@Service
public class UploadTrafficServiceScheduler
        extends SpringEventTriggeredServiceSchedulerBase<UploadTrafficService> {

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Autowired
    private UploadTrafficService services;

    @Override
    protected Set<UploadTrafficService> getServices() {
        return Set.of(services);
    }

    @Override
    protected TaskScheduler getScheduler() {
        return scheduler;
    }

}