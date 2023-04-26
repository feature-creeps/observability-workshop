package com.github.olly.workshop.trafficgen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;

import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public abstract class SpringEventTriggeredServiceSchedulerBase<T extends TriggeredService> {

    private final ApplicationContext applicationContextToReactOn;
    private final ConfigurationService configurationService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!Objects.equals(this.applicationContextToReactOn.getId(), event.getApplicationContext().getId())) {
            return;
        }

        getServices().forEach(this::startService);
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