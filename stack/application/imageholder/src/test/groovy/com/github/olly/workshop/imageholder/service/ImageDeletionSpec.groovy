package com.github.olly.workshop.imageholder.service

import com.github.olly.workshop.imageholder.model.Image
import spock.lang.Specification

/*
    this spec represents a test with spock mocks/stubs
 */

class ImageDeletionSpec extends Specification {

    def "cannot save more than 1000 images"() {
        given:
        ImageRepository imageRepository = Mock(ImageRepository)
        MetricsService metricsService = Mock(MetricsService)
        EventService eventService = Mock(EventService)
        ImageService imageService = new ImageService(
                imageRepository: imageRepository,
                metricsService: metricsService,
                eventService: eventService)


        and:
        def numberOfImages = 1000
        def testImage = new Image(id: 'test')

        and:
        1 * imageRepository.findAllIds() >> (1..numberOfImages).collect { new Image(id: it) }
        1 * imageRepository.findAllIds() >> (1..(numberOfImages - 1)).collect { new Image(id: it) }
        imageRepository.findById(_) >> { String id -> new Optional<Image>(new Image(id: id)) }

        when:
        imageService.save(testImage)

        then:
        1 * imageRepository.save(testImage)
        1 * imageRepository.deleteById(_)
    }
}
