package com.github.olly.workshop.imagerotator.adapter;

import static org.junit.Assert.assertEquals;
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
                    filename, "image/jpg", IOUtils.toByteArray(image.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void properImageShouldReturnValidResponse() throws IOException {
        ResponseEntity response = imageController.rotateImage(getMultipartImage("test.jpg"), "0");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.hasBody());
        assertTrue(response.getHeaders().get("content-type").stream().anyMatch(item -> "image/jpg".equals(item)));
    }

    @Test
    public void emptyImageShouldReturnBadRequest() throws IOException {
        MultipartFile emptyImage = new MockMultipartFile("file",
                "test.jpg", "image/jpg", new byte[] {});
        ResponseEntity response = imageController.rotateImage(emptyImage, "0");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void unknownContentTypeShouldReturnBadRequest() throws IOException {
        MultipartFile wrongContent = new MockMultipartFile("file",
                "test.jpg", "application/json", new byte[] { 0 });
        ResponseEntity response = imageController.rotateImage(wrongContent, "0");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}
