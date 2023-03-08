package com.github.olly.workshop.trafficgen.service;

import java.util.Optional;

import org.springframework.scheduling.Trigger;

public interface TriggeredService extends Runnable {

    long NO_CONFIG_VALUE = 0L;

    Optional<String> getKey();

    Trigger constructTrigger(long duration);

}