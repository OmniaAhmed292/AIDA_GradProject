package com.example.aida.service.ReviewService;

import com.example.aida.Entities.Product;
import com.example.aida.Entities.Reviews;
import com.example.aida.Repositories.ProductRepository;
import com.example.aida.Repositories.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;
    private final ReviewsRepository reviewsRepository;

    // Retrieve a single review
    public Reviews getReviewById(String productId, String reviewId) {//done
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Optional<Reviews> reviewOptional = reviewsRepository.findAll().stream()
                    .filter(review -> review.getId().equals(reviewId))
                    .findFirst();
            if (reviewOptional.isPresent()) {
                return reviewOptional.get();
            }
        } else {
            throw new RuntimeException("Product not found");
        }
        throw new RuntimeException("Review not found");
    }

    // Submit a new review
    public Reviews createReview(String productId, Reviews review) { //done
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            review.setCreatedAt(LocalDateTime.now());
            Reviews savedReview = reviewsRepository.save(review);
            existingProduct.getReviewsList().add(savedReview);
            productRepository.save(existingProduct);
            return savedReview;
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    // Update an existing review
    public Reviews updateReview(String productId, String reviewId, Reviews reviewDetails) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            List<Reviews> reviews = existingProduct.getReviewsList();
            reviews.stream()
                    .filter(review -> review.getId().equals(reviewId))
                    .forEach(review -> {
                        review.setBody(reviewDetails.getBody());
                        review.setRate(reviewDetails.getRate());
                        review.setUpdatedAt(LocalDateTime.now());
                    });
            productRepository.save(existingProduct);
            return reviewDetails;
        } else {
            throw new RuntimeException("Product not found");
        }
    }

     //Delete a review
     public void deleteReview(String productId, String reviewId) {// done
         Optional<Product> productOptional = productRepository.findById(productId);
         if (productOptional.isPresent()) {
             Product product = productOptional.get();
             List<Reviews> reviews = product.getReviewsList();
             Optional<Reviews> reviewOptional = reviewsRepository.findAll().stream()
                     .filter(review -> review.getId().equals(reviewId))
                     .findFirst();

             if (reviewOptional.isPresent()) {
                 reviews.remove(reviewOptional.get());
                 productRepository.save(product);
             } else {
                 throw new RuntimeException("Review not found");
             }
         } else {
             throw new RuntimeException("Product not found");
         }
     }

    // Retrieve all reviews by a user
    public List<Reviews> getReviewsByUserId(String userId) { //done
        List<Product> products = productRepository.findAll();
        return products.stream()
                .flatMap(product -> product.getReviewsList().stream())
                .filter(review -> review.getCustomerId().equals(userId))
                .collect(Collectors.toList());
    }


}
