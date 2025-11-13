package com.rentease.service;

import com.rentease.model.Review;
import com.rentease.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // Add review
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    // Get reviews for product
    public List<Review> getReviewsByProduct(String productId) {
        return reviewRepository.findByProductId(productId);
    }
}
