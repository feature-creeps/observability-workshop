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
public class ImageGrayscaleClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageGrayscaleClient.class);
    @Autowired
    RestTemplate restTemplate;


    public Image transform(Image image) {


        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("image")
                .filename("image")
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        fileMap.add(HttpHeaders.CONTENT_TYPE, image.getMimeType());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(image.getData(), fileMap);

        MultiValueMap<String, Object> theMulitpartRequest = new LinkedMultiValueMap<>();

        theMulitpartRequest.add("image", fileEntity);



        HttpHeaders theMultipartHeaders = new HttpHeaders();
        theMultipartHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(theMulitpartRequest, theMultipartHeaders);


        ResponseEntity<byte[]> response = restTemplate.exchange("http://imagegrayscale:8080/api/image/grayscale", HttpMethod.POST, requestEntity,byte[].class);


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