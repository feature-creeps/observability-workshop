package com.github.olly.workshop.imageorchestrator.service.clients;

import com.github.olly.workshop.imageorchestrator.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service
public class ImageRotatorClient {

        @Autowired
        RestTemplate restTemplate;

        @Value("${imagerotator.baseUrl}")
        private String imagerotatorBaseUrl;

        private static final Logger LOGGER = LoggerFactory.getLogger(ImageRotatorClient.class);

        public Image transform(Image image, String degrees) {

                LOGGER.info("Rotating image by {} degrees", degrees);

                MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
                ContentDisposition contentDisposition = ContentDisposition
                                .builder("form-data")
                                .name("image")
                                .filename("image")
                                .build();
                fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
                fileMap.add(HttpHeaders.CONTENT_TYPE, image.getMimeType());
                org.springframework.http.HttpEntity<byte[]> fileEntity = new org.springframework.http.HttpEntity<>(
                                image.getData(), fileMap);

                MultiValueMap<String, Object> theMulitpartRequest = new LinkedMultiValueMap<>();

                theMulitpartRequest.add("image", fileEntity);
                theMulitpartRequest.add("degrees", degrees);

                HttpHeaders theMultipartHeaders = new HttpHeaders();
                theMultipartHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                org.springframework.http.HttpEntity<MultiValueMap<String, Object>> requestEntity = new org.springframework.http.HttpEntity<>(
                                theMulitpartRequest, theMultipartHeaders);

                ResponseEntity<byte[]> response = restTemplate.exchange(
                                imagerotatorBaseUrl + "/api/image/rotate", HttpMethod.POST, requestEntity,
                                byte[].class);

                Collection<String> contentTypes = response.getHeaders().get("content-type");
                String contentType = "image/png";
                if (contentTypes.size() > 0) {
                        String[] temp = new String[contentTypes.size()];
                        contentTypes.toArray(temp);
                        contentType = temp[0];
                }

                return new Image(response.getBody(), contentType);

        }

}
