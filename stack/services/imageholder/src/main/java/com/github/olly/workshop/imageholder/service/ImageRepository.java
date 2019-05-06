package com.github.olly.workshop.imageholder.service;


import com.github.olly.workshop.imageholder.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
interface ImageRepository extends MongoRepository<Image, String>{

    List<Image> findByNameContaining(String regexp);

}