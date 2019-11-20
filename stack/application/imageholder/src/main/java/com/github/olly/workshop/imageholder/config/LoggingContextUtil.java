package com.github.olly.workshop.imageholder.config;

import com.github.olly.workshop.imageholder.model.Image;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class LoggingContextUtil {

    public void mdcPut(Image image) {
        image(image);
    }

    private void image(Image image) {
        if (image != null) {
            MDC.put("mimeType", image.getContentType());
            MDC.put("imageId", image.getId());
            MDC.put("imageName", image.getName());
        }
    }
}
