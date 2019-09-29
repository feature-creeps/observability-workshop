package com.github.olly.workshop.imageorchestrator.service;

import io.honeycomb.beeline.tracing.Beeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeelineService {

    @Autowired(required = false)
    private Beeline beeline;


    public void addFieldToActiveSpan(String key, Object value) {
        if (this.beeline != null) {
            beeline.getActiveSpan().addField(key, value);
        }
    }
}
