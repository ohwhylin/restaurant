package com.example.restaurant.service;

import com.example.restaurant.dto.user.UserRequestDto;
import com.example.restaurant.dto.user.UserResponseDto;
import com.example.restaurant.entity.Visitor;
import com.example.restaurant.mapper.UserMapper;
import com.example.restaurant.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAllUsers() {
        return visitorRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    public UserResponseDto getUserById(Long id) {
        return visitorRepository.findById(id)
                .map(userMapper::toResponseDto)
                .orElse(null);
    }

    public UserResponseDto createUser(UserRequestDto dto) {
        Visitor visitor = userMapper.toEntity(dto);
        Visitor saved = visitorRepository.save(visitor);
        return userMapper.toResponseDto(saved);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        return visitorRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.name());
                    existing.setAge(dto.age());
                    existing.setGender(dto.gender());
                    Visitor saved = visitorRepository.save(existing);
                    return userMapper.toResponseDto(saved);
                })
                .orElse(null);
    }

    public void deleteUser(Long id) {
        visitorRepository.deleteById(id);
    }
}
