package com.github.olly.workshop.imageholder.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "imagethumbnail", url = "${imagethumbnail.baseUrl}")
public interface ImageThumbnailClient {

    @RequestMapping(method = RequestMethod.DELETE, value = "api/images/{id}")
    ResponseEntity<byte[]> informThumbnail(@PathVariable("id") String id);
}
