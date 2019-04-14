package com.github.olly.workshop.imageorchestrator.service.clients;


import com.github.olly.workshop.imageorchestrator.model.Image;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageHolderUploadClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHolderUploadClient.class);

    public void upload(Image image) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("http://imageholder:8080/api/images");
        uploadFile.setEntity(imageToMultipart(image));
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(uploadFile);
            LOGGER.info("Successfully uploaded transformed image to the imageholder with id {}", IOUtils.toString(response.getEntity().getContent()));
        } catch (IOException e) {
            LOGGER.error("Failed uploading the transformed image to the imageholder", e);
        }
    }

    private HttpEntity imageToMultipart(Image image) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("image", image.getData(), ContentType.create(image.getMimeType()), "image");
        return builder.build();
    }
}
