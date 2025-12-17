package com.example.restaurant.service;

import com.example.restaurant.dto.restaurant.RestaurantRequestDto;
import com.example.restaurant.dto.restaurant.RestaurantResponseDto;
import com.example.restaurant.entity.CuisineType;
import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.mapper.RestaurantMapper;
import com.example.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    RestaurantMapper restaurantMapper;

    @InjectMocks
    RestaurantService restaurantService;

    @Test
    void getAllRestaurants_returnsListOfDtos() {
        Restaurant r1 = new Restaurant(1L, "Pasta House", "desc", CuisineType.ITALIAN,
                BigDecimal.valueOf(1200), BigDecimal.ZERO);
        Restaurant r2 = new Restaurant(2L, "Dragon Wok", "desc2", CuisineType.CHINESE,
                BigDecimal.valueOf(900), BigDecimal.valueOf(4));

        when(restaurantRepository.findAll()).thenReturn(List.of(r1, r2));

        RestaurantResponseDto d1 = new RestaurantResponseDto(1L, "Pasta House", "desc",
                CuisineType.ITALIAN, BigDecimal.valueOf(1200), BigDecimal.ZERO);
        RestaurantResponseDto d2 = new RestaurantResponseDto(2L, "Dragon Wok", "desc2",
                CuisineType.CHINESE, BigDecimal.valueOf(900), BigDecimal.valueOf(4));

        when(restaurantMapper.toResponseDto(r1)).thenReturn(d1);
        when(restaurantMapper.toResponseDto(r2)).thenReturn(d2);

        List<RestaurantResponseDto> result = restaurantService.getAllRestaurants();

        assertThat(result).containsExactly(d1, d2);
    }

    @Test
    void getRestaurantById_existing_returnsDto() {
        Restaurant r = new Restaurant(1L, "Pasta", null, CuisineType.ITALIAN,
                BigDecimal.TEN, BigDecimal.ZERO);
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(r));
        RestaurantResponseDto dto = new RestaurantResponseDto(1L, "Pasta", null,
                CuisineType.ITALIAN, BigDecimal.TEN, BigDecimal.ZERO);
        when(restaurantMapper.toResponseDto(r)).thenReturn(dto);

        RestaurantResponseDto result = restaurantService.getRestaurantById(1L);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void createRestaurant_savesAndReturnsDto() {
        RestaurantRequestDto request = new RestaurantRequestDto(null, "Pasta", "desc", CuisineType.ITALIAN, BigDecimal.TEN);
        Restaurant toSave = new Restaurant(null, "Pasta", "desc",
                CuisineType.ITALIAN, BigDecimal.TEN, BigDecimal.ZERO);
        Restaurant saved = new Restaurant(1L, "Pasta", "desc",
                CuisineType.ITALIAN, BigDecimal.TEN, BigDecimal.ZERO);
        RestaurantResponseDto dto = new RestaurantResponseDto(1L, "Pasta", "desc",
                CuisineType.ITALIAN, BigDecimal.TEN, BigDecimal.ZERO);

        when(restaurantMapper.toEntity(request)).thenReturn(toSave);
        when(restaurantRepository.save(toSave)).thenReturn(saved);
        when(restaurantMapper.toResponseDto(saved)).thenReturn(dto);

        RestaurantResponseDto result = restaurantService.createRestaurant(request);

        assertThat(result).isEqualTo(dto);
        verify(restaurantRepository).save(toSave);
    }

    @Test
    void deleteRestaurant_callsRepositoryDelete() {
        restaurantService.deleteRestaurant(3L);
        verify(restaurantRepository).deleteById(3L);
    }
}
