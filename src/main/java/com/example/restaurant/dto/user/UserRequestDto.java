package com.example.restaurant.dto.user;

import com.example.restaurant.entity.Gender;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotNull Long id,

        @Nullable
        @Size(max = 100)
        String name,

        @NotNull
        @Min(0)
        Integer age,

        @NotNull
        Gender gender
) {
}
