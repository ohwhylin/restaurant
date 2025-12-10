package com.example.restaurant.controller;

import com.example.restaurant.dto.restaurant.RestaurantRequestDto;
import com.example.restaurant.dto.restaurant.RestaurantResponseDto;
import com.example.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurants", description = "Операции с ресторанами")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "Получить список всех ресторанов")
    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/search/by-rating")
    @Operation(summary = "Поиск ресторанов с рейтингом не меньше заданного (по имени метода)")
    public List<RestaurantResponseDto> searchByRating(
            @RequestParam("minRating") BigDecimal minRating
    ) {
        return restaurantService.findRestaurantsByMinRating(minRating);
    }

    @GetMapping("/search/by-rating-query")
    @Operation(summary = "Поиск ресторанов с рейтингом не меньше заданного (через @Query JPQL)")
    public List<RestaurantResponseDto> searchByRatingQuery(
            @RequestParam("minRating") BigDecimal minRating
    ) {
        return restaurantService.findRestaurantsByMinRatingQuery(minRating);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ресторан по id")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Long id) {
        RestaurantResponseDto dto = restaurantService.getRestaurantById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Создать новый ресторан")
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@Valid @RequestBody RestaurantRequestDto dto) {
        return ResponseEntity.ok(restaurantService.createRestaurant(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить ресторан по id")
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequestDto dto) {
        RestaurantResponseDto updated = restaurantService.updateRestaurant(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ресторан по id")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }
}
