package com.github.olly.workshop.trafficgen.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.olly.workshop.trafficgen.service.ConfigurationService;
import com.github.olly.workshop.trafficgen.service.tranformation.TransformationTrafficService;
import com.github.olly.workshop.trafficgen.service.upload.UploadService;

@RestController
@RequestMapping(value = "/api/traffic/image")
public class TrafficGenController {
    @Autowired
    private UploadService randomUploadService;

    @Autowired
    private ConfigurationService configurationService;

    @PostMapping(value = "transform/start")
    public void transformStart(@RequestParam(value = "transformationsPerSecond") int transformationsPerSecond) {
        configurationService.set(TransformationTrafficService.SERVICE_KEY, transformationsPerSecond);
    }

    @PostMapping(value = "transform/stop")
    public void transformStop() {
        configurationService.set(TransformationTrafficService.SERVICE_KEY, 0);
    }

    @PostMapping(value = "upload")
    public void upload() {
        randomUploadService.uploadAllImages();
    }
}
