package com.github.olly.workshop.imageholder.service

import com.github.olly.workshop.imageholder.model.Image
import com.github.olly.workshop.imageholder.service.ImageRepository
import com.github.olly.workshop.imageholder.service.ImageService
import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.memory.MemoryBackend
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import spock.lang.Specification

/*
    this spec represents a spring boot integration test with spock and an in-memory mongo db
 */
@SpringBootTest
class ImageServiceSpec extends Specification {

    @Autowired
    ImageService imageService

    @Autowired
    ImageRepository imageRepository

    @MockBean
    MetricsService metricsService

    @MockBean
    EventService eventService

    def setup() {
        imageRepository.deleteAll()
    }

    def "image can be saved"() {
        given: 'we have a test image'
        Image image = new Image(data: 'test'.bytes)

        when: 'we save the image'
        image = imageService.save(image)

        then: 'the image is in the db'
        imageRepository.findById(image.id)
    }

    def "image can be deleted"() {
        given: 'we have a test image'
        Image image = new Image(data: 'test'.bytes)

        when: 'we save it to the db'
        image = imageService.save(image)

        and: 'then delete it from the db again'
        imageService.deleteImageById(image.id)

        then: 'the image is not in the db anymore'
        !imageRepository.findById(image.id).isPresent()
    }

    def "all images can be deleted"() {
        given: 'we have a test image'
        Image image = new Image(data: 'test'.bytes)

        when: 'we save it three times'
        Image image1 = imageService.save(image)
        Image image2 = imageService.save(image)
        Image image3 = imageService.save(image)

        then: 'we have three images in the db'
        imageRepository.count() == 3

        when: 'we delete all images'
        imageService.deleteAllImages()

        then: 'the three images are not existent in the db'
        !imageRepository.findById(image1.id).isPresent()
        !imageRepository.findById(image2.id).isPresent()
        !imageRepository.findById(image3.id).isPresent()
        imageRepository.count() == 0
    }

    def "cannot persist more than 1000 images"() {
        given: 'we have a test image'
        Image image = new Image(data: 'test'.bytes)

        when: 'we save it 1000 times'
        1000.times { imageService.save(image) }

        then: 'there are 1000 images in the db'
        imageRepository.count() == 1000

        when: 'when we save it once more'
        image = imageService.save(image)

        then: 'then the one is persisted and we still have 1000 images in the db as another one was deleted'
        imageRepository.findById(image.id).isPresent()
        imageRepository.count() == 1000
    }

    @TestConfiguration
    static class Config extends AbstractMongoConfiguration {
        @Override
        protected String getDatabaseName() {
            return "test"
        }

        @Override
        public MongoClient mongoClient() {
            MongoServer mongoServer = new MongoServer(new MemoryBackend())
            mongoServer.bind()
            return new MongoClient(new ServerAddress(mongoServer.getLocalAddress()))
        }
    }
}
