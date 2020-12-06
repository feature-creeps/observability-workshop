package com.github.olly.workshop.imagegrayscale.service;

import org.slf4j.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Event {

    private static Map<String, Object> spans = baseSpan();
    private static Map<String, String> stringSpans = new HashMap<String, String>();

    private static final Logger LOGGER = LoggerFactory.getLogger("event");
    private static final Marker eventMarker = MarkerFactory.getMarker("EVENT");

    private static final String EVENT_BASE_FIELD = "event";

    void addField(String key, Object value) {
        spans.put(key, value);
    }

    void addFields(Map<String, Object> fields) {
        spans.putAll(fields);
    }

    Object getField(String key) {
        return spans.get(key);
    }

    Set<String> getKeys() {
        return spans.keySet();
    }

    void publish(String message) {
        getStringSpans().forEach(MDC::put);
        LOGGER.info(eventMarker, message);
        MDC.clear();
    }

    private Map<String, String> getStringSpans() {
        spans.forEach((key, value) -> stringSpans.put(
                EVENT_BASE_FIELD + "." + key.replaceAll("\\.", "_"),
                valueToString(value)));
        return stringSpans;
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
