package com.github.olly.workshop.trafficgen.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.olly.workshop.trafficgen.service.TransformationTrafficGenService;

@RestController
@RequestMapping(value = "/api/traffic/image")
public class TrafficGenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficGenController.class);

    @Autowired
    private TransformationTrafficGenService transformationTrafficGenService;

    @PostMapping(value = "transform")
    public void transform() {
        transformationTrafficGenService.generateTraffic();
    }
}
