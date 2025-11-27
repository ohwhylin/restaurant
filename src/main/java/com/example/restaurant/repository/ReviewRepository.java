package com.example.restaurant.repository;

import com.example.restaurant.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {

    private final List<Review> reviews = new ArrayList<>();

    public void save(Review review) {
        for (int i = 0; i < reviews.size(); i++) {
            Review r = reviews.get(i);
            if (r.getVisitorId().equals(review.getVisitorId()) && r.getRestaurantId().equals(review.getRestaurantId())) {
                reviews.set(i, review);
                return;
            }
        }
        reviews.add(review);
    }

    public void removeById(Long visitorId, Long restaurantId) {
        reviews.removeIf(r -> r.getVisitorId().equals(visitorId) && r.getRestaurantId().equals(restaurantId)
        );
    }

    public Review findById(Long visitorId, Long restaurantId) {
        for (Review r : reviews) {
            if (r.getVisitorId().equals(visitorId) && r.getRestaurantId().equals(restaurantId)) {
                return r;
            }
        }
        return null;
    }

    public List<Review> findAll() {
        return reviews;
    }
}
