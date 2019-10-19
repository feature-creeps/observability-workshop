package com.github.olly.workshop.imageorchestrator.service;

import io.honeycomb.beeline.tracing.Beeline;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.context.WebApplicationContext.SCOPE_APPLICATION;

@Service
@Scope(SCOPE_APPLICATION)
public class EventService {

    @Autowired(required = false)
    private Beeline beeline;

    private Map<String, Object> spans = baseSpan();
    private Map<String, String> stringSpans = new HashMap<String, String>();

    private static final Logger LOGGER = LoggerFactory.getLogger("EventLogger");
    private static final Marker eventMarker = MarkerFactory.getMarker("EVENT");

    public void addFieldToActiveSpan(String key, Object value) {
        // honeycomb, optional
        if (this.beeline != null) {
            beeline.getActiveSpan().addField(key, value);
        }

        // add single field info to our event
        putSpan(key, value);
    }

    public void addFieldsToActiveSpan(Map<String, String> fields) {
        fields.forEach(this::putSpan);
    }

    public void publishEvent(String message, Map<String, Object> additionalFields) {
        publishAsLog(message, additionalFields);
        clearSpans();
    }

    public Map<String, Object> getSpans() {
        return spans;
    }

    private static Map<String, Object> baseSpan() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "event");
        return map;
    }

    private void putSpan(String key, Object value) {
        spans.put(key, value);
    }

    private void addSpansToMdc() {
        stringSpans.forEach(MDC::put);
    }

    private void addAdditionalFieldsToSpan(Map<String, Object> additionalFields) {
        if (!CollectionUtils.isEmpty(additionalFields)) {
            additionalFields.forEach(this::putSpan);
        }
    }

    private void publishAsLog(String message, Map<String, Object> additionalFields) {
        addAdditionalFieldsToSpan(additionalFields);

        copySpansToStringSpans();
        addSpansToMdc();
        LOGGER.info(eventMarker, message);
    }

    private void copySpansToStringSpans() {
        spans.forEach((key, value) -> stringSpans.put(
                "span" + "." + key.replaceAll("\\.", "_"),
                valueToString(value)));
    }

    private String valueToString(Object value) {
        if (value instanceof LocalDateTime) {
            return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format((LocalDateTime) value);
        } else {
            return String.valueOf(value);
        }
    }

    private void clearSpans() {
        spans = baseSpan();
    }
}
