package ch.zhaw.carewe.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.carewe.model.Rating;

public interface RatingRepository extends MongoRepository<Rating, String> {
    
}
