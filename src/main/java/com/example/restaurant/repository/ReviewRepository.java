package com.example.restaurant.repository;

import com.example.restaurant.entity.Review;
import com.example.restaurant.entity.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    List<Review> findByRestaurant_Id(Long restaurantId);
}
