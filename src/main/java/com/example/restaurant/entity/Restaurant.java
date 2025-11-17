package com.example.restaurant.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @NotNull
    private Long id;
    @NotNull
    private String name;
    @Nullable
    private String description;
    @NotNull
    private CuisineType cuisineType;
    @NotNull
    private BigDecimal averageCheck;
    @NotNull
    private BigDecimal rating;
}
