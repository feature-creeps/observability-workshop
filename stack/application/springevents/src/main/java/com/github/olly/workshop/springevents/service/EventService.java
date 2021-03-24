package com.github.olly.workshop.springevents.service;

import io.honeycomb.beeline.tracing.Beeline;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EventService {

    @Autowired(required = false)
    private Beeline beeline;

    @Value("${events.enabled:true}")
    private Boolean EVENTS_ENABLED;

    private Event activeEvent;
    private final String EVENT_ID_KEY = "event.id";
    private static final String startedAt = "startedAt";

    private final Logger LOGGER = LoggerFactory.getLogger("event");
    private final Marker eventMarker = MarkerFactory.getMarker("EVENT");

    public Event newEvent(Event.EventTrigger trigger) {
        String id = UUID.randomUUID().toString();
        MDC.put(EVENT_ID_KEY, id);
        this.activeEvent = new Event(id, trigger);
        this.activeEvent.addField(startedAt, LocalDateTime.now());
        return this.activeEvent;
    }

    public void addFieldToActiveEvent(String key, Object value) {
        // honeycomb, optional
        if (this.beeline != null) {
            beeline.getActiveSpan().addField(key, value);
        }

        // add single field info to our event
        Event event = getActiveEvent();
        event.addField(key, value);
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
        publish(message);
        cleanUpEventFromContext();
    }

    private void publish(String message) {
        activeEvent.getStringFields().forEach(MDC::put);

        setEventDuration();
        LOGGER.info(eventMarker, message);
        MDC.clear();
    }

    private void setEventDuration() {
        final LocalDateTime now = LocalDateTime.now();
        if (getFieldFromActiveEvent(startedAt) != null) {
            final Long duration = Duration.between((LocalDateTime) getFieldFromActiveEvent(startedAt), now).toMillis();
            addFieldToActiveEvent("duration_ms", duration);
        }
        addFieldToActiveEvent("finishedAt", now);
    }

    private void cleanUpEventFromContext() {
        MDC.clear();
        activeEvent = null;
    }

    private Event getActiveEvent() {
        if (activeEvent == null) {
            activeEvent = newEvent(Event.EventTrigger.UNKNOWN);
            MDC.put(EVENT_ID_KEY, activeEvent.getId());
        }
        return activeEvent;
    }
}
