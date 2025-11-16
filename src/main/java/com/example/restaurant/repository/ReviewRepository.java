package com.example.restaurant.repository;

import com.example.restaurant.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {

    private final List<Review> reviews = new ArrayList<>();

    public void save(Review review) {
        reviews.add(review);
    }

    public void remove(Review review) {
        reviews.remove(review);
    }

    public List<Review> findAll() {
        return reviews;
    }

    public Review findById(Long visitorId, Long restaurantId) {
        for (Review review : reviews) {
            if (review.getVisitorId().equals(visitorId)
                    && review.getRestaurantId().equals(restaurantId)) {
                return review;
            }
        }
        return null;
    }
}
