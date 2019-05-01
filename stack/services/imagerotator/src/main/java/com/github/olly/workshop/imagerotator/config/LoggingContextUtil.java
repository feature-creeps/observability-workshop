package com.github.olly.workshop.imagerotator.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LoggingContextUtil {

    public void mdcPut(String mimeType, String degrees) {
        mdcClear();
        MDC.put("mimeType", mimeType);
        MDC.put("degrees", degrees);
    }

    public void mdcClear() {
        MDC.clear();
    }
}
