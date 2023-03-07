package com.github.olly.workshop.trafficgen.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.olly.workshop.trafficgen.service.TransformationTrafficGenService;
import com.github.olly.workshop.trafficgen.service.UploadService;

@RestController
@RequestMapping(value = "/api/traffic/image")
public class TrafficGenController {

    @Autowired
    private TransformationTrafficGenService transformationTrafficGenService;

    @Autowired
    private UploadService randomUploadService;

    @PostMapping(value = "transform/start")
    public void transformStart(@RequestParam(value = "transformationsPerSecond") int transformationsPerSecond) {
        synchronized (transformationTrafficGenService) {
            transformationTrafficGenService.setTransformationsPerSecond(transformationsPerSecond);
        }
    }

    @PostMapping(value = "transform/stop")
    public void transformStop() {
        synchronized (transformationTrafficGenService) {
            transformationTrafficGenService.setTransformationsPerSecond(0);
        }
    }

    @PostMapping(value = "upload")
    public void upload() {
        randomUploadService.uploadAllImages();
    }
}
