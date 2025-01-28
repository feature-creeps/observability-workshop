package com.github.olly.workshop.trafficgen.service.upload;

import com.github.olly.workshop.trafficgen.clients.ImageHolderClient;
import com.github.olly.workshop.trafficgen.service.TrafficGenMetricsService;
import com.github.olly.workshop.trafficgen.service.tranformation.TransformationService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final ImageHolderClient imageHolderClient;
    private final TrafficGenMetricsService trafficGenMetricsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationService.class);

    @Async
    public void uploadAllImages() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
        try {
            Resource[] resources = resolver.getResources("classpath*:/images/*");
            for (Resource resource : resources) {
                if (imageHolderClient.findWithNameContaining(resource.getFilename()).getBody().size() > 0) {
                    LOGGER.info("Image " + resource.getFilename() + " already exists, skipping upload");
                } else {
                    try {
                        imageHolderClient.uploadImage(loadImage(resource), resource.getFilename());
                        trafficGenMetricsService.uploadPerformed(true);
                        LOGGER.info("Upload of " + resource.getFilename() + " successful.");
                    } catch (FeignException e) {
                        trafficGenMetricsService.uploadPerformed(false);
                        LOGGER.warn("Upload of " + resource.getFilename() + " failed due to " + e.getMessage(), e);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Cannot load images as resources.", e);
        }
    }

    private MultipartFile loadImage(Resource resource) throws IOException {
        String contentType = "image/" + resource.getFilename().split("_")[0];
        InputStream input = resource.getInputStream();
        return new MockMultipartFile("file",
                resource.getFilename(), contentType, IOUtils.toByteArray(input));
    }
}