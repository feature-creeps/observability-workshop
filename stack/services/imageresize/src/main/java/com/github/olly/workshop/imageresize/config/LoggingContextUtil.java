package com.github.olly.workshop.imageresize.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LoggingContextUtil {

    public void mdcPut(String mimeType, String factor) {
        MDC.put("mimeType", mimeType);
        MDC.put("factor", factor);
    }
}
