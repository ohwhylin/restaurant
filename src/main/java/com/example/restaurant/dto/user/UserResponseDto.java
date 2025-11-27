package com.example.restaurant.dto.user;

import com.example.restaurant.entity.Gender;
import jakarta.annotation.Nullable;

public record UserResponseDto(
        Long id,
        @Nullable String name,
        Integer age,
        Gender gender
) {
}
