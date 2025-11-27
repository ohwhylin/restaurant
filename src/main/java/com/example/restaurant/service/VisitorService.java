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
        return visitorRepository.findAll().stream().map(userMapper::toResponseDto).toList();
    }

    public UserResponseDto getUserById(Long id) {
        Visitor visitor = visitorRepository.findById(id);
        if (visitor == null) {
            return null;
        }
        return userMapper.toResponseDto(visitor);
    }

    public UserResponseDto createUser(UserRequestDto dto) {
        Visitor visitor = userMapper.toEntity(dto);
        visitorRepository.save(visitor);
        return userMapper.toResponseDto(visitor);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        Visitor existing = visitorRepository.findById(id);
        if (existing == null) {
            return null;
        }

        existing.setName(dto.name());
        existing.setAge(dto.age());
        existing.setGender(dto.gender());

        visitorRepository.save(existing);
        return userMapper.toResponseDto(existing);
    }

    public void deleteUser(Long id) {
        visitorRepository.removeById(id);
    }
}
