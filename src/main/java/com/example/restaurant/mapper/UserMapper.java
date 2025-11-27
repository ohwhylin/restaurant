package com.example.restaurant.mapper;

import com.example.restaurant.dto.user.UserRequestDto;
import com.example.restaurant.dto.user.UserResponseDto;
import com.example.restaurant.entity.Visitor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    Visitor toEntity(UserRequestDto dto);

    UserResponseDto toResponseDto(Visitor entity);
}
