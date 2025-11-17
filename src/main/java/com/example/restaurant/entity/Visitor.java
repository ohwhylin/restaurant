package com.example.restaurant.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.annotation.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {

    @NotNull
    private Long id;
    @Nullable
    private String name;
    @NotNull
    private Integer age;
    @NotNull
    private Gender gender;
}
