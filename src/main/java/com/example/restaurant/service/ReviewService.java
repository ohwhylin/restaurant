package com.example.restaurant.service;

import com.example.restaurant.dto.review.ReviewRequestDto;
import com.example.restaurant.dto.review.ReviewResponseDto;
import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.entity.Review;
import com.example.restaurant.mapper.ReviewMapper;
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
    private final ReviewMapper reviewMapper;

    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream().map(reviewMapper::toResponseDto).toList();
    }

    public ReviewResponseDto getReview(Long visitorId, Long restaurantId) {
        Review review = reviewRepository.findById(visitorId, restaurantId);
        if (review == null) {
            return null;
        }
        return reviewMapper.toResponseDto(review);
    }

    public ReviewResponseDto createReview(ReviewRequestDto dto) {
        Review review = reviewMapper.toEntity(dto);
        reviewRepository.save(review);
        recalculateRating(dto.restaurantId());
        return reviewMapper.toResponseDto(review);
    }

    public ReviewResponseDto updateReview(Long visitorId, Long restaurantId, ReviewRequestDto dto) {
        Review existing = reviewRepository.findById(visitorId, restaurantId);
        if (existing == null) {
            return null;
        }

        existing.setScore(dto.score());
        existing.setText(dto.text());

        reviewRepository.save(existing);
        recalculateRating(restaurantId);

        return reviewMapper.toResponseDto(existing);
    }

    public void deleteReview(Long visitorId, Long restaurantId) {
        reviewRepository.removeById(visitorId, restaurantId);
        recalculateRating(restaurantId);
    }

    private void recalculateRating(Long restaurantId) {
        List<Review> all = reviewRepository.findAll();
        int sum = 0;
        int count = 0;

        for (Review r : all) {
            if (r.getRestaurantId().equals(restaurantId)) {
                sum += r.getScore();
                count++;
            }
        }

        BigDecimal rating = (count == 0) ? BigDecimal.ZERO : BigDecimal.valueOf((double) sum / count);

        for (Restaurant r : restaurantRepository.findAll()) {
            if (r.getId().equals(restaurantId)) {
                r.setRating(rating);
                restaurantRepository.save(r);
                break;
            }
        }
    }
}
