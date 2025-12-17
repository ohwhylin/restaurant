package com.example.restaurant.controller;

import com.example.restaurant.dto.review.ReviewRequestDto;
import com.example.restaurant.dto.review.ReviewResponseDto;
import com.example.restaurant.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ReviewService reviewService;

    @Test
    void getAllReviews_returnsList() throws Exception {
        ReviewResponseDto r = new ReviewResponseDto(1L, 1L, 5, "ok");
        when(reviewService.getAllReviews()).thenReturn(List.of(r));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].score").value(5));
    }

    @Test
    void getReview_whenNotFound_returns404() throws Exception {
        when(reviewService.getReview(eq(10L), eq(20L))).thenReturn(null);

        mockMvc.perform(get("/api/reviews/10/20"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReview_returnsCreated() throws Exception {
        ReviewResponseDto response = new ReviewResponseDto(1L, 1L, 5, "ok");
        when(reviewService.createReview(any(ReviewRequestDto.class))).thenReturn(response);

        String body = """
                {
                  "visitorId": 1,
                  "restaurantId": 1,
                  "score": 5,
                  "text": "ok"
                }
                """;

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(5));
    }

    @Test
    void updateReview_returnsUpdated() throws Exception {
        ReviewResponseDto response = new ReviewResponseDto(1L, 1L, 4, "updated");
        when(reviewService.updateReview(eq(1L), eq(1L), any(ReviewRequestDto.class)))
                .thenReturn(response);

        String body = """
                {
                  "visitorId": 1,
                  "restaurantId": 1,
                  "score": 4,
                  "text": "updated"
                }
                """;

        mockMvc.perform(put("/api/reviews/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score").value(4));
    }

    @Test
    void deleteReview_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/reviews/1/1"))
                .andExpect(status().isNoContent());
    }
}
