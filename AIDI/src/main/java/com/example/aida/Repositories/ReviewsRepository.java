package com.example.aida.Repositories;

import com.example.aida.Entities.Reviews;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewsRepository extends MongoRepository<Reviews, String> {
//get all reviews
    public List<Reviews> findAll();
    //    public Reviews findByCustomerId(String customerId);
//    public Reviews findByProductId(String productId);
//    public Reviews findByReviewId(String reviewId);
}
