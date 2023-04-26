package com.github.olly.workshop.imageorchestrator.service.clients;

import com.github.olly.workshop.imageorchestrator.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ImageFlipClient {

    RestTemplate restTemplate;

    @Value("${imageflip.baseUrl}")
    private String imageflipBaseUrl;

    public Image transform(Image image, boolean vertical, boolean horizontal) {

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
        multiPartRequest.add("vertical", new HttpEntity<>(vertical));
        multiPartRequest.add("horizontal", new HttpEntity<>(horizontal));

        HttpHeaders theMultipartHeaders = new HttpHeaders();
        theMultipartHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multiPartRequest,
                theMultipartHeaders);

        ResponseEntity<byte[]> response = restTemplate.exchange(imageflipBaseUrl + "/api/image/flip",
                HttpMethod.POST, requestEntity, byte[].class);

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