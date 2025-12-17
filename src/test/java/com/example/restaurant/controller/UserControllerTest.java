package com.example.restaurant.controller;

import com.example.restaurant.dto.user.UserRequestDto;
import com.example.restaurant.dto.user.UserResponseDto;
import com.example.restaurant.entity.Gender;
import com.example.restaurant.service.VisitorService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    VisitorService visitorService;

    @Test
    void getAllUsers_returnsJsonArray() throws Exception {
        UserResponseDto u1 = new UserResponseDto(1L, "Иван", 25, Gender.MALE);
        UserResponseDto u2 = new UserResponseDto(2L, null, 30, Gender.FEMALE);

        when(visitorService.getAllUsers()).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getUserById_whenNotFound_returns404() throws Exception {
        when(visitorService.getUserById(10L)).thenReturn(null);

        mockMvc.perform(get("/api/users/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_returnsCreatedUser() throws Exception {
        UserResponseDto response = new UserResponseDto(1L, "Иван", 25, Gender.MALE);
        when(visitorService.createUser(any(UserRequestDto.class))).thenReturn(response);

        String body = """
                {
                  "id": 1,
                  "name": "Иван",
                  "age": 25,
                  "gender": "MALE"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateUser_returnsUpdated() throws Exception {
        UserResponseDto response = new UserResponseDto(1L, "Петя", 30, Gender.MALE);
        when(visitorService.updateUser(eq(1L), any(UserRequestDto.class))).thenReturn(response);

        String body = """
                {
                  "id": 1,
                  "name": "Петя",
                  "age": 30,
                  "gender": "MALE"
                }
                """;

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Петя"));
    }

    @Test
    void deleteUser_returnsOk() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }
}
