package com.example.restaurant.service;

import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.entity.Review;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public void save(Review review) {
        reviewRepository.save(review);
        recalculateRestaurantRating(review.getRestaurantId());
    }

    public void remove(Review review) {
        reviewRepository.remove(review);
        recalculateRestaurantRating(review.getRestaurantId());
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    private void recalculateRestaurantRating(Long restaurantId) {
        List<Review> allReviews = reviewRepository.findAll();

        int sum = 0;
        int count = 0;

        for (Review review : allReviews) {
            if (review.getRestaurantId().equals(restaurantId)) {
                sum += review.getScore();
                count++;
            }
        }

        BigDecimal newRating;
        if (count == 0) {
            newRating = BigDecimal.ZERO;
        } else {
            double average = (double) sum / count;
            newRating = BigDecimal.valueOf(average);
        }

        for (Restaurant restaurant : restaurantRepository.findAll()) {
            if (restaurant.getId().equals(restaurantId)) {
                restaurant.setRating(newRating);
                break;
            }
        }
    }
}
