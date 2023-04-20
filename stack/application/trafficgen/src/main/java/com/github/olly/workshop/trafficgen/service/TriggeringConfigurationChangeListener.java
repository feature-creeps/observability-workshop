package com.github.olly.workshop.trafficgen.service;

import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;

public class TriggeringConfigurationChangeListener {

    private volatile ScheduledFuture<?> future;

    private final TriggeredService service;
    private final TaskScheduler taskScheduler;

    public TriggeringConfigurationChangeListener(TriggeredService service, TaskScheduler taskScheduler) {
        this.service = service;
        this.taskScheduler = taskScheduler;
    }

    public Optional<String> keyToReactOn() {
        return service.getKey();
    }

    public synchronized void onConfigurationChange(long newValue) {
        if (future != null) {
            future.cancel(false);
        }

        Trigger trigger = service.constructTrigger(newValue);
        future = taskScheduler.schedule(service, trigger);
    }
}