package com.github.olly.workshop.imageresize.service;

import io.honeycomb.beeline.tracing.Beeline;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class EventService {

    @Autowired(required = false)
    private Beeline beeline;

    @Value("${events.enabled:true}")
    private Boolean EVENTS_ENABLED;

    private static Map<String, Event> events = new HashMap<String, Event>();
    private static final String EVENT_ID_KEY = "event.id";


    public String newEvent() {
        String id = UUID.randomUUID().toString();
        MDC.put(EVENT_ID_KEY, id);
        events.put(id, new Event());
        return id;
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
        if (!EVENTS_ENABLED) {
            return;
        }
        getActiveEvent().publish(message);
        // clean up
        MDC.clear();
        events.remove(getActiveEventId());
    }


    private String getActiveEventId() {
        final String id = MDC.get(EVENT_ID_KEY);
        if (id == null) {
            return newEvent();
        } else {
            return id;
        }
    }

    private Event getActiveEvent() {
        String id = getActiveEventId();
        return events.get(id);
    }

    private void putSpan(String id, String key, Object value) {
        events.get(id).addField(key, value);
    }
}
