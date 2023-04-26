package com.github.olly.workshop.trafficgen.service;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.time.Duration;
import java.util.Optional;

public interface TriggeredService extends Runnable {

    long NO_CONFIG_VALUE = 0L;
    long S_IN_MS = 1000L;
    Trigger NEVER_RUN_TRIGGER = new CronTrigger("0 0 0 31 2 *");
    Trigger ONCE_NOW_TRIGGER = new PeriodicTrigger(Duration.ofSeconds(Long.MAX_VALUE));

    Optional<String> getKey();

    Trigger constructTrigger(long duration);

}