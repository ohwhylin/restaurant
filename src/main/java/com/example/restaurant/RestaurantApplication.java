package com.example.restaurant;

import com.example.restaurant.entity.*;
import com.example.restaurant.service.RestaurantService;
import com.example.restaurant.service.ReviewService;
import com.example.restaurant.service.VisitorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class RestaurantApplication {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }

    @PostConstruct
    public void initData() {
        // посетители
        Visitor v1 = new Visitor(1L, "Иван", 25, Gender.MALE);
        Visitor v2 = new Visitor(2L, null, 30, Gender.FEMALE); // анонимный
        Visitor v3 = new Visitor(3L, "Иванна", 19, Gender.MALE);

        visitorService.save(v1);
        visitorService.save(v2);
        visitorService.save(v3);

        // рестораны
        Restaurant r1 = new Restaurant(
                1L,
                "Pasta House",
                "Итальянский ресторан",
                CuisineType.ITALIAN,
                BigDecimal.valueOf(1200),
                BigDecimal.ZERO
        );

        Restaurant r2 = new Restaurant(
                2L,
                "Dragon Wok",
                "Китайская кухня",
                CuisineType.CHINESE,
                BigDecimal.valueOf(900),
                BigDecimal.ZERO
        );

        restaurantService.save(r1);
        restaurantService.save(r2);

        // оценки
        Review rev1 = new Review(1L, 1L, 5, "Отличная паста!");
        Review rev2 = new Review(2L, 1L, 4, null);
        Review rev3 = new Review(3L, 2L, 3, "Неплохо, но могло быть лучше");

        reviewService.save(rev1);
        reviewService.save(rev2);
        reviewService.save(rev3);

        // проверка: вывод в консоль
        System.out.println("Посетители:");
        for (Visitor v : visitorService.findAll()) {
            System.out.println(v);
        }

        System.out.println("Рестораны:");
        List<Restaurant> restaurants = restaurantService.findAll();
        for (Restaurant r : restaurants) {
            System.out.println(r.getName() + " | рейтинг = " + r.getRating());
        }

        System.out.println("Отзывы:");
        for (Review review : reviewService.findAll()) {
            System.out.println(review);
        }
    }
}
