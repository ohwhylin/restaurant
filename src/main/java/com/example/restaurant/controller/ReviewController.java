package com.example.restaurant.controller;

import com.example.restaurant.dto.review.ReviewRequestDto;
import com.example.restaurant.dto.review.ReviewResponseDto;
import com.example.restaurant.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Операции с отзывами о ресторанах")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Получить все отзывы")
    public List<ReviewResponseDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{visitorId}/{restaurantId}")
    @Operation(summary = "Получить отзыв по id посетителя и id ресторана")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long visitorId, @PathVariable Long restaurantId) {
        ReviewResponseDto dto = reviewService.getReview(visitorId, restaurantId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Создать новый отзыв")
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewRequestDto dto) {
        return ResponseEntity.ok(reviewService.createReview(dto));
    }

    @PutMapping("/{visitorId}/{restaurantId}")
    @Operation(summary = "Обновить отзыв по id посетителя и id ресторана")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long visitorId, @PathVariable Long restaurantId, @Valid @RequestBody ReviewRequestDto dto) {
        ReviewResponseDto updated = reviewService.updateReview(visitorId, restaurantId, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{visitorId}/{restaurantId}")
    @Operation(summary = "Удалить отзыв по id посетителя и id ресторана")
    public ResponseEntity<Void> deleteReview(@PathVariable Long visitorId, @PathVariable Long restaurantId) {
        reviewService.deleteReview(visitorId, restaurantId);
        return ResponseEntity.ok().build();
    }
}
