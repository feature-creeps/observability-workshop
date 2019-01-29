package com.github.olly.workshop.imagegrayscale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    @Autowired
    MetricsService metricsService;

    public byte[] grayscale(MultipartFile image) {
        try {
            InputStream in = new ByteArrayInputStream(image.getBytes());
            String formatName = image.getContentType().split("/")[1];
            final BufferedImage grayScaleImage = grayscale(ImageIO.read(in));


            final byte[] imageBytes = bufferedImageToByteArray(grayScaleImage, formatName);

            metricsService.imageToGrayscale(image.getContentType(),imageBytes.length);

            return imageBytes;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private BufferedImage grayscale(BufferedImage source) {
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        return op.filter(source, null);
    }

    private byte[] bufferedImageToByteArray(BufferedImage image, String formatname) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, formatname, baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }
}
