package com.example.restaurant.dto.restaurant;

import com.example.restaurant.entity.CuisineType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record RestaurantRequestDto(
        @NotNull Long id,

        @NotBlank
        String name,

        @Nullable
        String description,

        @NotNull
        CuisineType cuisineType,

        @NotNull
        @PositiveOrZero
        BigDecimal averageCheck
) {
}
