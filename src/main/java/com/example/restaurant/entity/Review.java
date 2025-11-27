package com.example.restaurant.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @NotNull
    private Long visitorId;
    @NotNull
    private Long restaurantId;
    @NotNull
    private Integer score;
    @NotNull
    private String text;
}
