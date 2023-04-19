package com.github.olly.workshop.trafficgen.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.github.olly.workshop.trafficgen.model.*;

@FeignClient(value = "imageorchestrator", url = "${imageorchestrator.baseUrl}")
public interface ImageOrchestratorClient {

    @RequestMapping(method = RequestMethod.POST, value = "api/images/transform")
    ResponseEntity transform(TransformationRequest transformationRequest);
}
