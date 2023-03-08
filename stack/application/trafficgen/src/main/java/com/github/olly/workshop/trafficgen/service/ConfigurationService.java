package com.github.olly.workshop.trafficgen.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {
    private Map<String, Long> configs = new HashMap<>();
    private Map<String, List<TriggeringConfigurationChangeListener>> listeners = new HashMap<>();

    public Long get(String key) {
        return configs.get(key);
    }

    public Map<String, Long> getAll() {
        return new HashMap<>(configs);
    }

    public void set(String key, long value) {
        configs.put(key, value);

        listeners.getOrDefault(key, Collections.emptyList())
                .forEach(listener -> listener.onConfigurationChange(value));
    }

    public void addListener(TriggeringConfigurationChangeListener listener) {
        listener.keyToReactOn()
                .ifPresent(key -> listeners.computeIfAbsent(key, k -> new ArrayList<>()).add(listener));
    }
}