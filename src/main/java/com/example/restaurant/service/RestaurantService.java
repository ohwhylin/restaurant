package com.example.restaurant.service;

import com.example.restaurant.dto.restaurant.RestaurantRequestDto;
import com.example.restaurant.dto.restaurant.RestaurantResponseDto;
import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.mapper.RestaurantMapper;
import com.example.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantRepository.findAll()
                .stream()
                .map(restaurantMapper::toResponseDto)
                .toList();
    }

    public RestaurantResponseDto getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toResponseDto)
                .orElse(null);
    }

    public RestaurantResponseDto createRestaurant(RestaurantRequestDto dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurant.setRating(BigDecimal.ZERO);
        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.toResponseDto(saved);
    }

    public RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto dto) {
        return restaurantRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.name());
                    existing.setDescription(dto.description());
                    existing.setCuisineType(dto.cuisineType());
                    existing.setAverageCheck(dto.averageCheck());
                    Restaurant saved = restaurantRepository.save(existing);
                    return restaurantMapper.toResponseDto(saved);
                })
                .orElse(null);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    public List<RestaurantResponseDto> findRestaurantsByMinRating(BigDecimal minRating) {
        return restaurantRepository.findByRatingGreaterThanEqual(minRating)
                .stream()
                .map(restaurantMapper::toResponseDto)
                .toList();
    }

    public List<RestaurantResponseDto> findRestaurantsByMinRatingQuery(BigDecimal minRating) {
        return restaurantRepository.findWithMinRating(minRating)
                .stream()
                .map(restaurantMapper::toResponseDto)
                .toList();
    }

}
