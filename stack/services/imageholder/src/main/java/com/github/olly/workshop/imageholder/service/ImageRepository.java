package com.github.olly.workshop.imageholder.service;


import com.github.olly.workshop.imageholder.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ImageRepository extends MongoRepository<Image, String> {
}