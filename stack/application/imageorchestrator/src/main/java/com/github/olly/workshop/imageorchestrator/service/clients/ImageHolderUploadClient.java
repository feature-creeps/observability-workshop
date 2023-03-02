package com.github.olly.workshop.imageorchestrator.service.clients;

import com.github.olly.workshop.imageorchestrator.config.LogTraceContextUtil;
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

@Service
public class ImageHolderUploadClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LogTraceContextUtil contextUtil;

    @Value("${imageholder.baseUrl}")
    private String imageholderBaseUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHolderUploadClient.class);

    public void upload(Image image, String name) {

        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("image")
                .filename("image")
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        fileMap.add(HttpHeaders.CONTENT_TYPE, image.getMimeType());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(image.getData(), fileMap);

        MultiValueMap<String, Object> multiPartRequest = new LinkedMultiValueMap<>();

        multiPartRequest.add("image", fileEntity);
        multiPartRequest.add("name", new HttpEntity<>(name));

        HttpHeaders theMultipartHeaders = new HttpHeaders();
        theMultipartHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multiPartRequest,
                theMultipartHeaders);

        ResponseEntity<String> response = restTemplate.exchange(imageholderBaseUrl + "/api/images",
                HttpMethod.POST, requestEntity, String.class);

        image.setId(extractImageId(response));

        contextUtil.put(image);
        LOGGER.info("Successfully uploaded transformed image to the imageholder with id {}", image.getId());
    }

    private String extractImageId(ResponseEntity<String> response) {
        if (response != null && response.getBody() != null) {
            String[] split = response.getBody().split(" ");
            return split[split.length - 1];
        }
        return "";
    }
}
