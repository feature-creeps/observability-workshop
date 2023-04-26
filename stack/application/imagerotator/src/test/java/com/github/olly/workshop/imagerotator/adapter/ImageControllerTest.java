package com.github.olly.workshop.imagerotator.adapter;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ImageControllerTest {

    @Autowired
    ImageController imageController;

    @Autowired
    private final ResourceLoader resourceLoader = null;

    private MultipartFile getMultipartImage(String filename) {
        Resource image = resourceLoader.getResource("classpath:" + filename);
        try {
            return new MockMultipartFile("file",
                    filename, "image/" + filename.split("\\.")[1], IOUtils.toByteArray(image.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void jpgImageShouldReturnInvalidResponse() throws IOException {
        ResponseEntity response = imageController.rotateImage(getMultipartImage("test.jpg"), "0");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.hasBody());
        assertTrue(response.getHeaders().get("content-type").stream().anyMatch(item -> "image/jpg".equals(item)));
        // ISSUE: rotating jpeg images don't work. This is intentional.
        byte[] data = (byte[]) response.getBody();
        assertEquals(data.length, 0);
    }

    @Test
    public void pngImageShouldReturnValidResponse() throws IOException {
        ResponseEntity response = imageController.rotateImage(getMultipartImage("test.png"), "0");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.hasBody());
        assertTrue(response.getHeaders().get("content-type").stream().anyMatch(item -> "image/png".equals(item)));
        byte[] data = (byte[]) response.getBody();
        assertNotEquals(data.length, 0);
    }

    @Test
    public void emptyImageShouldReturnBadRequest() throws IOException {
        MultipartFile emptyImage = new MockMultipartFile("file",
                "test.jpg", "image/jpg", new byte[]{});
        ResponseEntity response = imageController.rotateImage(emptyImage, "0");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void unknownContentTypeShouldReturnBadRequest() throws IOException {
        MultipartFile wrongContent = new MockMultipartFile("file",
                "test.jpg", "application/json", new byte[]{0});
        ResponseEntity response = imageController.rotateImage(wrongContent, "0");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
