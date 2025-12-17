package com.example.restaurant.service;

import com.example.restaurant.dto.user.UserRequestDto;
import com.example.restaurant.dto.user.UserResponseDto;
import com.example.restaurant.entity.Gender;
import com.example.restaurant.entity.Visitor;
import com.example.restaurant.mapper.UserMapper;
import com.example.restaurant.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitorServiceTest {

    @Mock
    VisitorRepository visitorRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    VisitorService visitorService;

    @Test
    void getAllUsers_returnsListOfDtos() {
        Visitor v1 = new Visitor(1L, "Иван", 25, Gender.MALE);
        Visitor v2 = new Visitor(2L, null, 30, Gender.FEMALE);

        when(visitorRepository.findAll()).thenReturn(List.of(v1, v2));
        UserResponseDto d1 = new UserResponseDto(1L, "Иван", 25, Gender.MALE);
        UserResponseDto d2 = new UserResponseDto(2L, null, 30, Gender.FEMALE);
        when(userMapper.toResponseDto(v1)).thenReturn(d1);
        when(userMapper.toResponseDto(v2)).thenReturn(d2);

        List<UserResponseDto> result = visitorService.getAllUsers();

        assertThat(result).containsExactly(d1, d2);
        verify(visitorRepository).findAll();
        verify(userMapper, times(2)).toResponseDto(any());
    }

    @Test
    void getUserById_existing_returnsDto() {
        Visitor v = new Visitor(1L, "Иван", 25, Gender.MALE);
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(v));

        UserResponseDto dto = new UserResponseDto(1L, "Иван", 25, Gender.MALE);
        when(userMapper.toResponseDto(v)).thenReturn(dto);

        UserResponseDto result = visitorService.getUserById(1L);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getUserById_notExisting_returnsNull() {
        when(visitorRepository.findById(10L)).thenReturn(Optional.empty());

        UserResponseDto result = visitorService.getUserById(10L);

        assertThat(result).isNull();
    }

    @Test
    void createUser_savesAndReturnsDto() {
        UserRequestDto request = new UserRequestDto(null, "Иван", 25, Gender.MALE);
        Visitor toSave = new Visitor(null, "Иван", 25, Gender.MALE);
        Visitor saved = new Visitor(1L, "Иван", 25, Gender.MALE);
        UserResponseDto response = new UserResponseDto(1L, "Иван", 25, Gender.MALE);

        when(userMapper.toEntity(request)).thenReturn(toSave);
        when(visitorRepository.save(toSave)).thenReturn(saved);
        when(userMapper.toResponseDto(saved)).thenReturn(response);

        UserResponseDto result = visitorService.createUser(request);

        assertThat(result).isEqualTo(response);
        verify(visitorRepository).save(toSave);
    }

    @Test
    void deleteUser_callsRepositoryDelete() {
        visitorService.deleteUser(5L);
        verify(visitorRepository).deleteById(5L);
    }
}
