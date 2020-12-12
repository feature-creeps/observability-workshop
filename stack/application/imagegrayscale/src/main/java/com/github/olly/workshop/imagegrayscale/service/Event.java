package com.github.olly.workshop.imagegrayscale.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Event {

    private Map<String, Object> fields = baseSpan();
    private Map<String, String> stringFields = new HashMap<String, String>();

    private final Logger LOGGER = LoggerFactory.getLogger("event");
    private final Marker eventMarker = MarkerFactory.getMarker("EVENT");
    private final String EVENT_BASE_FIELD = "event";

    public Event(String id) {
        addField("id", id);
    }

    void addField(String key, Object value) {
        fields.put(key, value);
    }

    void addFields(Map<String, Object> fields) {
        this.fields.putAll(fields);
    }

    Object getField(String key) {
        return fields.get(key);
    }

    Set<String> getKeys() {
        return fields.keySet();
    }

    void publish(String message) {
        getStringFields().forEach(MDC::put);
        LOGGER.info(eventMarker, message);
        MDC.clear();
    }

    private Map<String, String> getStringFields() {
        fields.forEach((key, value) -> stringFields.put(
                EVENT_BASE_FIELD + "." + key.replaceAll("\\.", "_"),
                valueToString(value)));
        return stringFields;
    }

    private String valueToString(Object value) {
        if (value instanceof LocalDateTime) {
            return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format((LocalDateTime) value);
        } else {
            return String.valueOf(value);
        }
    }

    private static Map<String, Object> baseSpan() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "event");
        return map;
    }
}
