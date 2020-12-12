package com.github.olly.workshop.imagegrayscale.service;

import io.honeycomb.beeline.tracing.Beeline;
import java.util.Map;
import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EventService {

    @Autowired(required = false)
    private Beeline beeline;

    @Value("${events.enabled:true}")
    private Boolean EVENTS_ENABLED;

    private Event activeEvent;
    private final String EVENT_ID_KEY = "event.id";

    public String newEvent() {
        String id = UUID.randomUUID().toString();
        MDC.put(EVENT_ID_KEY, id);
        activeEvent = new Event(id);
        return id;
    }

    public void addFieldToActiveEvent(String key, Object value) {
        // honeycomb, optional
        if (this.beeline != null) {
            beeline.getActiveSpan().addField(key, value);
        }

        // add single field info to our event
        String id = getActiveEventId();
        putField(id, key, value);
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
        activeEvent = null;
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
        return activeEvent;
    }

    private void putField(String id, String key, Object value) {
        activeEvent.addField(key, value);
    }
}
