package com.example.restaurant.service;

import com.example.restaurant.dto.review.ReviewRequestDto;
import com.example.restaurant.dto.review.ReviewResponseDto;
import com.example.restaurant.entity.*;
import com.example.restaurant.mapper.ReviewMapper;
import com.example.restaurant.repository.RestaurantRepository;
import com.example.restaurant.repository.ReviewRepository;
import com.example.restaurant.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    VisitorRepository visitorRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    ReviewMapper reviewMapper;

    @InjectMocks
    ReviewService reviewService;

    @Test
    void getAllReviews_returnsListOfDtos() {
        Restaurant r = new Restaurant(1L, "Pasta", null, CuisineType.ITALIAN,
                BigDecimal.TEN, BigDecimal.ZERO);
        Visitor v = new Visitor(1L, "Иван", 25, Gender.MALE);

        ReviewId id = new ReviewId(1L, 1L);
        Review review = new Review(id, v, r, 5, "ok");

        when(reviewRepository.findAll()).thenReturn(List.of(review));

        ReviewResponseDto dto = new ReviewResponseDto(1L, 1L, 5, "ok");
        when(reviewMapper.toResponseDto(review)).thenReturn(dto);

        List<ReviewResponseDto> result = reviewService.getAllReviews();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getReviewsPage_returnsPage() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("score").descending());
        Restaurant r = new Restaurant(1L, "Pasta", null, CuisineType.ITALIAN,
                BigDecimal.TEN, BigDecimal.ZERO);
        Visitor v = new Visitor(1L, "Иван", 25, Gender.MALE);
        ReviewId id = new ReviewId(1L, 1L);
        Review review = new Review(id, v, r, 5, "ok");

        when(reviewRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(review), pageable, 1));

        ReviewResponseDto dto = new ReviewResponseDto(1L, 1L, 5, "ok");
        when(reviewMapper.toResponseDto(review)).thenReturn(dto);

        var page = reviewService.getReviewsPage(0, 2, "desc");

        assertThat(page.getContent()).containsExactly(dto);
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    void getReview_existing_returnsDto() {
        Restaurant r = new Restaurant(1L, "Pasta", null, CuisineType.ITALIAN,
                BigDecimal.TEN, BigDecimal.ZERO);
        Visitor v = new Visitor(1L, "Иван", 25, Gender.MALE);
        ReviewId id = new ReviewId(1L, 1L);
        Review review = new Review(id, v, r, 4, "ok");

        when(reviewRepository.findById(id)).thenReturn(Optional.of(review));

        ReviewResponseDto dto = new ReviewResponseDto(1L, 1L, 4, "ok");
        when(reviewMapper.toResponseDto(review)).thenReturn(dto);

        ReviewResponseDto result = reviewService.getReview(1L, 1L);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void createReview_savesAndReturnsDto() {
        ReviewRequestDto request = new ReviewRequestDto(1L, 1L, 5, "ok");

        Visitor v = new Visitor(1L, "Иван", 25, Gender.MALE);
        Restaurant r = new Restaurant(1L, "Pasta", null, CuisineType.ITALIAN,
                BigDecimal.TEN, BigDecimal.ZERO);

        when(visitorRepository.findById(1L)).thenReturn(Optional.of(v));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(r));

        ReviewId id = new ReviewId(1L, 1L);
        Review review = new Review(id, v, r, 5, "ok");

        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toResponseDto(review))
                .thenReturn(new ReviewResponseDto(1L, 1L, 5, "ok"));

        ReviewResponseDto result = reviewService.createReview(request);

        assertThat(result.score()).isEqualTo(5);
        verify(reviewRepository).save(any(Review.class));
    }
}
