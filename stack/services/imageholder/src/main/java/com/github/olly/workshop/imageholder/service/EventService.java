package com.github.olly.workshop.imageholder.service;

import io.honeycomb.beeline.tracing.Beeline;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class EventService {

    @Autowired(required = false)
    private Beeline beeline;

    private static Map<String, Event> events = new HashMap<String, Event>();
    private static final String EVENT_ID_KEY = "event.id";


    public void newEvent() {
        String id = UUID.randomUUID().toString();
        MDC.put(EVENT_ID_KEY, id);
        events.put(id, new Event());
    }

    public void addFieldToActiveEvent(String key, Object value) {
        // honeycomb, optional
        if (this.beeline != null) {
            beeline.getActiveSpan().addField(key, value);
        }

        // add single field info to our event
        String id = getActiveEventId();
        putSpan(id, key, value);
    }

    public void addFieldsToActiveEvent(Map<String, Object> fields) {
        getActiveEvent().addFields(fields);
    }

    public Object getFieldFromActiveEvent(String key) {
        return getActiveEvent().getField(key);
    }

    public void publishEvent(String message) {
        getActiveEvent().publish(message);
        // clean up
        MDC.remove(EVENT_ID_KEY);
        events.remove(getActiveEventId());
    }


    private String getActiveEventId() {
        return MDC.get(EVENT_ID_KEY);
    }

    private Event getActiveEvent() {
        String id = getActiveEventId();
        return events.get(id);
    }

    private void putSpan(String id, String key, Object value) {
        events.get(id).addField(key, value);
    }
}
