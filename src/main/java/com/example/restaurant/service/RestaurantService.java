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
        return restaurantRepository.findAll().stream().map(restaurantMapper::toResponseDto).toList();
    }

    public RestaurantResponseDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id);
        if (restaurant == null) {
            return null;
        }
        return restaurantMapper.toResponseDto(restaurant);
    }

    public RestaurantResponseDto createRestaurant(RestaurantRequestDto dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurant.setRating(BigDecimal.ZERO);
        restaurantRepository.save(restaurant);
        return restaurantMapper.toResponseDto(restaurant);
    }

    public RestaurantResponseDto updateRestaurant(Long id, RestaurantRequestDto dto) {
        Restaurant existing = restaurantRepository.findById(id);
        if (existing == null) {
            return null;
        }

        existing.setName(dto.name());
        existing.setDescription(dto.description());
        existing.setCuisineType(dto.cuisineType());
        existing.setAverageCheck(dto.averageCheck());

        restaurantRepository.save(existing);
        return restaurantMapper.toResponseDto(existing);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.removeById(id);
    }
}
