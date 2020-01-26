package com.github.olly.workshop.imageflip.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    @Autowired
    MetricsService metricsService;

    @Autowired
    private EventService eventService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    public byte[] flip(MultipartFile file, boolean vertical, boolean horizontal) {
        this.eventService.addFieldToActiveEvent("transformation.flip.vertical", String.valueOf(vertical));
        this.eventService.addFieldToActiveEvent("transformation.flip.horizontal", String.valueOf(horizontal));
        try {
            InputStream in = new ByteArrayInputStream(file.getBytes());
            String formatName = file.getContentType().split("/")[1];
            final BufferedImage flippedImage = flip(ImageIO.read(in), vertical, horizontal);
            final byte[] imageBytes = bufferedImageToByteArray(flippedImage, formatName);

            metricsService.imageFlipped(file.getContentType(), String.valueOf(vertical), String.valueOf(horizontal));
            this.eventService.addFieldToActiveEvent("content.type", file.getContentType()) ;

            return imageBytes;
        } catch (IOException e) {
            LOGGER.error("Failed flipping image", e);
        }

        return null;
    }

    private BufferedImage flip(BufferedImage image, boolean vertical, boolean horizontal) {
        AffineTransform affineTransform = AffineTransform.getScaleInstance(horizontal ? -1 : 1, vertical ? -1 : 1);
        final int tx = horizontal ? -image.getWidth(null) : 0;
        final int ty = vertical ? -image.getHeight(null) : 0;

        affineTransform.translate(tx, ty);
        AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        image = op.filter(image, null);
        return image;
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
