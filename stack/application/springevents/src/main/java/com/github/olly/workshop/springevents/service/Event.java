package com.github.olly.workshop.springevents.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Event {

    private Map<String, Object> fields = baseFields();
    private Map<String, String> stringFields = new HashMap<String, String>();

    private final String EVENT_BASE_FIELD = "event";

    public enum EventTrigger {
        HTTP,
        MESSAGE,
        CRON,
        FILE,
        UNKNOWN
    }

    public Event(String id, EventTrigger trigger) {
        addField("id", id);
        addField("trigger", trigger);
    }

    String getId() {
        return getField("id").toString();
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

    Map<String, String> getStringFields() {
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

    private static Map<String, Object> baseFields() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "event");
        return map;
    }
}
