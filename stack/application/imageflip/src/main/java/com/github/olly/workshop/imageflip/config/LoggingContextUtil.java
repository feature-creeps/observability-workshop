package com.github.olly.workshop.imageflip.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LoggingContextUtil {

    public void mdcPut(String mimeType, boolean vertical, boolean horizontal) {
        put("mimeType", mimeType);
        put("vertical", vertical);
        put("horizontal",horizontal);
    }

    private void put(String key, Object value) {
        MDC.put(key, String.valueOf(value));
    }
}
