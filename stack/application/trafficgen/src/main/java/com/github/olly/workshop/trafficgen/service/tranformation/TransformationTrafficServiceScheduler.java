package com.github.olly.workshop.trafficgen.service.tranformation;

import com.github.olly.workshop.trafficgen.service.ConfigurationService;
import com.github.olly.workshop.trafficgen.service.SpringEventTriggeredServiceSchedulerBase;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TransformationTrafficServiceScheduler
        extends SpringEventTriggeredServiceSchedulerBase<TransformationTrafficService> {

    private final ThreadPoolTaskScheduler scheduler;
    private final TransformationTrafficService services;

    public TransformationTrafficServiceScheduler(ApplicationContext applicationContextToReactOn, ConfigurationService configurationService, ThreadPoolTaskScheduler scheduler, TransformationTrafficService services) {
        super(applicationContextToReactOn, configurationService);
        this.scheduler = scheduler;
        this.services = services;
    }

    @Override
    protected Set<TransformationTrafficService> getServices() {
        return Set.of(services);
    }

    @Override
    protected TaskScheduler getScheduler() {
        return scheduler;
    }

}