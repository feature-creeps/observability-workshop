package com.github.olly.workshop.imageflip.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LoggingContextUtil {

    public void mdcPut(String mimeType, boolean vertical, boolean horizontal) {
        mdcClear();
        MDC.put("mimeType", mimeType);
        MDC.put("vertical", String.valueOf(vertical));
        MDC.put("horizontal", String.valueOf(horizontal));
    }

    public void mdcClear() {
        MDC.clear();
    }
}
