package com.example.restaurant.controller;

import com.example.restaurant.dto.restaurant.RestaurantRequestDto;
import com.example.restaurant.dto.restaurant.RestaurantResponseDto;
import com.example.restaurant.entity.CuisineType;
import com.example.restaurant.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    RestaurantService restaurantService;

    @Test
    void getAllRestaurants_returnsList() throws Exception {
        RestaurantResponseDto r1 = new RestaurantResponseDto(
                1L, "Pasta", "desc", CuisineType.ITALIAN,
                BigDecimal.TEN, BigDecimal.ZERO
        );

        when(restaurantService.getAllRestaurants()).thenReturn(List.of(r1));

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Pasta"));
    }

    @Test
    void getRestaurantById_whenNotFound_returns404() throws Exception {
        when(restaurantService.getRestaurantById(10L)).thenReturn(null);

        mockMvc.perform(get("/api/restaurants/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createRestaurant_returnsCreated() throws Exception {
        RestaurantResponseDto response = new RestaurantResponseDto(
                1L, "Pasta", "desc", CuisineType.ITALIAN,
                BigDecimal.TEN, BigDecimal.ZERO
        );

        when(restaurantService.createRestaurant(any(RestaurantRequestDto.class)))
                .thenReturn(response);

        String body = """
                {
                  "id": 1,
                  "name": "Pasta",
                  "description": "desc",
                  "cuisineType": "ITALIAN",
                  "averageCheck": 10
                }
                """;

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateRestaurant_returnsUpdated() throws Exception {
        RestaurantResponseDto response = new RestaurantResponseDto(
                1L, "Pasta2", "desc2", CuisineType.ITALIAN,
                BigDecimal.valueOf(20), BigDecimal.ZERO
        );

        when(restaurantService.updateRestaurant(eq(1L), any(RestaurantRequestDto.class)))
                .thenReturn(response);

        String body = """
                {
                  "id": 1,
                  "name": "Pasta2",
                  "description": "desc2",
                  "cuisineType": "ITALIAN",
                  "averageCheck": 20
                }
                """;

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pasta2"));
    }

    @Test
    void deleteRestaurant_returnsOk() throws Exception {
        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isOk());
    }
}
