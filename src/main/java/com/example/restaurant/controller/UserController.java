package com.example.restaurant.controller;

import com.example.restaurant.dto.user.UserRequestDto;
import com.example.restaurant.dto.user.UserResponseDto;
import com.example.restaurant.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Операции с посетителями (пользователями)")
public class UserController {

    private final VisitorService visitorService;

    @GetMapping
    @Operation(summary = "Получить всех посетителей")
    public List<UserResponseDto> getAllUsers() {
        return visitorService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить посетителя по id")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto dto = visitorService.getUserById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Создать нового посетителя")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(visitorService.createUser(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные посетителя по id")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto dto) {
        UserResponseDto updated = visitorService.updateUser(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить посетителя по id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        visitorService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
