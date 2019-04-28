package com.github.olly.workshop.imageorchestrator.service.clients;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageRotatorClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageRotatorClient.class);

    public HttpEntity transform(HttpEntity multipart) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost("http://imagerotator:8080/api/image/rotate");
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(uploadFile);
            return response.getEntity();
        } catch (IOException e) {
            LOGGER.error("Received error response from imagerotator", e);
            return null;
        }
    }
}
