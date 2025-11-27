package com.example.restaurant.mapper;

import com.example.restaurant.dto.review.ReviewRequestDto;
import com.example.restaurant.dto.review.ReviewResponseDto;
import com.example.restaurant.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toEntity(ReviewRequestDto dto);

    ReviewResponseDto toResponseDto(Review entity);
}
