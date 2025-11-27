package com.example.restaurant.dto.review;

public record ReviewResponseDto(
        Long visitorId,
        Long restaurantId,
        Integer score,
        String text
) {
}
