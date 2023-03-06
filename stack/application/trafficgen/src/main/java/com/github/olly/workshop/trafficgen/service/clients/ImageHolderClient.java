package com.github.olly.workshop.trafficgen.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import java.util.Collection;

@FeignClient(value = "imageholder", url = "${imageholder.baseUrl}")
public interface ImageHolderClient {

    @GetMapping(value = "api/images/{id}")
    ResponseEntity<byte[]> getImage(@PathVariable("id") String id);

    @GetMapping(value = "api/images/random")
    ResponseEntity getRandomImage();

    @PostMapping(value = "api/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity uploadImage(@PathVariable("image") MultipartFile file,
            @PathVariable(value = "name") String name);

    @GetMapping(value = "api/images/nameContaining/{fragment}")
    ResponseEntity<Collection> findWithNameContaining(@PathVariable("fragment") String fragment);
}
