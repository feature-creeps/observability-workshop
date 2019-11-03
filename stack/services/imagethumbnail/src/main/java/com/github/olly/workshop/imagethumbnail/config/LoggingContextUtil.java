package com.github.olly.workshop.imagethumbnail.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LoggingContextUtil {

    public void mdcPut(String mimeType) {
        MDC.put("mimeType", mimeType);
    }
}
