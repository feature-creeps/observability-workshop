package com.github.olly.workshop.imageflip.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageControllerTest {

    @Autowired
    ImageController imageController;

    @Autowired
    private ResourceLoader resourceLoader = null;

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
    public void jpgImageShouldReturnValidResponse() throws IOException {
        ResponseEntity response = imageController.flipImage(getMultipartImage("test.jpg"), true, false);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.hasBody());
        assertTrue(response.getHeaders().get("content-type").stream().anyMatch(item -> "image/jpg".equals(item)));
        byte[] data = (byte[]) response.getBody();
        assertNotEquals(data.length, 0);
    }

    @Test
    public void pngImageShouldReturnValidResponse() throws IOException {
        ResponseEntity response = imageController.flipImage(getMultipartImage("test.png"), true, false);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.hasBody());
        assertTrue(response.getHeaders().get("content-type").stream().anyMatch(item -> "image/png".equals(item)));
        byte[] data = (byte[]) response.getBody();
        assertNotEquals(data.length, 0);
    }

    @Test
    public void emptyImageShouldReturnBadRequest() throws IOException {
        MultipartFile emptyImage = new MockMultipartFile("file",
                "test.jpg", "image/jpg", new byte[] {});
        ResponseEntity response = imageController.flipImage(emptyImage, true, false);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void unknownContentTypeShouldReturnBadRequest() throws IOException {
        MultipartFile wrongContent = new MockMultipartFile("file",
                "test.jpg", "application/json", new byte[] { 0 });
        ResponseEntity response = imageController.flipImage(wrongContent, true, false);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
