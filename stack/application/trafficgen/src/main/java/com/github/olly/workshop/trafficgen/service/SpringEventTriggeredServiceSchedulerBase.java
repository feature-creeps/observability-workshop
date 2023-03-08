package com.github.olly.workshop.trafficgen.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.context.event.EventListener;

public abstract class SpringEventTriggeredServiceSchedulerBase<T extends TriggeredService> {

    @Autowired
    private ApplicationContext applicationContextToReactOn;

    @Autowired
    private ConfigurationService configurationService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!this.applicationContextToReactOn.getId().equals(event.getApplicationContext().getId())) {
            return;
        }

        getServices().stream().forEach(this::startService);
    }

    private final void startService(T service) {

        TriggeringConfigurationChangeListener listener = new TriggeringConfigurationChangeListener(service,
                getScheduler());

        service.getKey().ifPresent(key -> configurationService.addListener(listener));

        long configValue = service.getKey()
                .map(configurationService::get)
                .orElse(TriggeredService.NO_CONFIG_VALUE);

        // initial start
        listener.onConfigurationChange(configValue);
    }

    protected abstract Set<T> getServices();

    protected abstract TaskScheduler getScheduler();
}