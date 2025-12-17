package com.example.restaurant.service;

import com.example.restaurant.dto.review.ReviewRequestDto;
import com.example.restaurant.dto.review.ReviewResponseDto;
import com.example.restaurant.entity.*;
import com.example.restaurant.mapper.ReviewMapper;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.ReviewRepository;
import com.example.restaurant.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final VisitorRepository visitorRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReviewMapper reviewMapper;

    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toResponseDto)
                .toList();
    }

    public Page<ReviewResponseDto> getReviewsPage(int page, int size, String direction) {
        Sort.Direction dir = "asc".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, "score"));

        Page<Review> reviewPage = reviewRepository.findAll(pageable);

        List<ReviewResponseDto> content = reviewPage.getContent()
                .stream()
                .map(reviewMapper::toResponseDto)
                .toList();

        return new PageImpl<>(
                content,
                pageable,
                reviewPage.getTotalElements()
        );
    }

    public ReviewResponseDto getReview(Long visitorId, Long restaurantId) {
        ReviewId id = new ReviewId(visitorId, restaurantId);
        return reviewRepository.findById(id)
                .map(reviewMapper::toResponseDto)
                .orElse(null);
    }

    public ReviewResponseDto createReview(ReviewRequestDto dto) {
        Visitor visitor = visitorRepository.findById(dto.visitorId()).orElse(null);
        Restaurant restaurant = restaurantRepository.findById(dto.restaurantId()).orElse(null);
        if (visitor == null || restaurant == null) {
            return null;
        }

        ReviewId id = new ReviewId(dto.visitorId(), dto.restaurantId());

        Review review = new Review();
        review.setId(id);
        review.setVisitor(visitor);
        review.setRestaurant(restaurant);
        review.setScore(dto.score());
        review.setText(dto.text());

        Review saved = reviewRepository.save(review);
        recalculateRating(restaurant.getId());
        return reviewMapper.toResponseDto(saved);
    }

    public ReviewResponseDto updateReview(Long visitorId, Long restaurantId, ReviewRequestDto dto) {
        ReviewId id = new ReviewId(visitorId, restaurantId);
        Review existing = reviewRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }

        existing.setScore(dto.score());
        existing.setText(dto.text());

        Review saved = reviewRepository.save(existing);
        recalculateRating(restaurantId);
        return reviewMapper.toResponseDto(saved);
    }

    public void deleteReview(Long visitorId, Long restaurantId) {
        ReviewId id = new ReviewId(visitorId, restaurantId);
        Review existing = reviewRepository.findById(id).orElse(null);
        if (existing != null) {
            Long restaurantIdValue = existing.getRestaurant().getId();
            reviewRepository.deleteById(id);
            recalculateRating(restaurantIdValue);
        }
    }

    private void recalculateRating(Long restaurantId) {
        List<Review> reviews = reviewRepository.findByRestaurant_Id(restaurantId);
        if (reviews.isEmpty()) {
            restaurantRepository.findById(restaurantId).ifPresent(r -> {
                r.setRating(BigDecimal.ZERO);
                restaurantRepository.save(r);
            });
            return;
        }

        int sum = reviews.stream()
                .mapToInt(Review::getScore)
                .sum();

        BigDecimal rating = BigDecimal.valueOf((double) sum / reviews.size());

        restaurantRepository.findById(restaurantId).ifPresent(r -> {
            r.setRating(rating);
            restaurantRepository.save(r);
        });
    }
}
