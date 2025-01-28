package com.github.olly.workshop.trafficgen.service.upload;

import com.github.olly.workshop.trafficgen.service.ConfigurationService;
import com.github.olly.workshop.trafficgen.service.SpringEventTriggeredServiceSchedulerBase;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UploadTrafficServiceScheduler
        extends SpringEventTriggeredServiceSchedulerBase<UploadTrafficService> {

    private final ThreadPoolTaskScheduler scheduler;
    private final UploadTrafficService services;

    public UploadTrafficServiceScheduler(ApplicationContext applicationContextToReactOn, ConfigurationService configurationService, ThreadPoolTaskScheduler scheduler, UploadTrafficService services) {
        super(applicationContextToReactOn, configurationService);
        this.scheduler = scheduler;
        this.services = services;
    }

    @Override
    protected Set<UploadTrafficService> getServices() {
        return Set.of(services);
    }

    @Override
    protected TaskScheduler getScheduler() {
        return scheduler;
    }

}