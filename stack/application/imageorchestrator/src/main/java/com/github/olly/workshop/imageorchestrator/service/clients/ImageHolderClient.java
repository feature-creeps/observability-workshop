package com.github.olly.workshop.imageorchestrator.service.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "imageholder", url = "imageholder:8080")
public interface ImageHolderClient {

    @RequestMapping(method = RequestMethod.GET, value = "api/images/{id}")
    ResponseEntity<byte[]> getImage(@PathVariable("id") String id);
}
