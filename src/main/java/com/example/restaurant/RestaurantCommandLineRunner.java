package com.example.restaurant;

import com.example.restaurant.entity.Restaurant;
import com.example.restaurant.entity.Review;
import com.example.restaurant.entity.Visitor;
import com.example.restaurant.service.RestaurantService;
import com.example.restaurant.service.ReviewService;
import com.example.restaurant.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantCommandLineRunner implements CommandLineRunner {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @Override
    public void run(String... args) {
        System.out.println("Проверка через CommandLineRunner");

        List<Visitor> visitors = visitorService.findAll();
        List<Restaurant> restaurants = restaurantService.findAll();
        List<Review> reviews = reviewService.findAll();

        System.out.println("Количество посетителей: " + visitors.size());
        System.out.println("Количество ресторанов: " + restaurants.size());
        System.out.println("Количество отзывов: " + reviews.size());

        System.out.println("Рестораны и их рейтинги (из CommandLineRunner):");
        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant.getName() + " | рейтинг = " + restaurant.getRating());
        }
    }
}
