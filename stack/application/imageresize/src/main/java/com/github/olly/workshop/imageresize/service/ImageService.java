package com.github.olly.workshop.imageresize.service;

import com.github.olly.workshop.springevents.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    @Autowired
    ImageResizeMetricsService imageResizeMetricsService;

    @Autowired
    private EventService eventService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public byte[] resize(MultipartFile file, Double factor) {
        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            String formatName = file.getContentType().split("/")[1];
            final BufferedImage resizedImage = resize(ImageIO.read(in), factor, !isPng(formatName));
            this.eventService.addFieldToActiveEvent("transformation.resize.factor", String.valueOf(factor));
            final byte[] imageBytes = bufferedImageToByteArray(resizedImage, formatName);

            imageResizeMetricsService.imageResized(file.getContentType(), String.valueOf(factor));

            return imageBytes;
        } catch (IOException e) {
            this.eventService.addFieldToActiveEvent("app.error", 1);
            this.eventService.addFieldToActiveEvent("app.exception", e);
            LOGGER.error("Failed resizing image", e);
        }

        return null;
    }

    private boolean isPng(String formatName) {
        return formatName.toLowerCase().equals("png");
    }

    private BufferedImage resize(BufferedImage image, Double factor, boolean preserveAlpha) {

        int scaledWidth = (int) (image.getWidth() * factor);
        int scaledHeight = (int) (image.getHeight() * factor);

        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    private byte[] bufferedImageToByteArray(BufferedImage image, String formatname) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // ISSUE: OpenJDK does not have a native JPEG encoder, so it will fail at this point
        ImageIO.write(image, formatname, baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }
}
