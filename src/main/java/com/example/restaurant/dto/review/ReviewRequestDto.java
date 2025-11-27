package com.example.restaurant.dto.review;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewRequestDto(
        @NotNull Long visitorId,
        @NotNull Long restaurantId,

        @NotNull
        @Min(1)
        @Max(5)
        Integer score,

        @Nullable
        @Size(max = 1000)
        String text
) {
}
