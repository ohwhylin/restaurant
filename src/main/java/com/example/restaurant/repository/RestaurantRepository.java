package com.example.restaurant.repository;

import com.example.restaurant.entity.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantRepository {

    private final List<Restaurant> restaurants = new ArrayList<>();

    public void save(Restaurant restaurant) {
        for (int i = 0; i < restaurants.size(); i++) {
            if (restaurants.get(i).getId().equals(restaurant.getId())) {
                restaurants.set(i, restaurant);
                return;
            }
        }
        restaurants.add(restaurant);
    }

    public void removeById(Long id) {
        restaurants.removeIf(r -> r.getId().equals(id));
    }

    public Restaurant findById(Long id) {
        for (Restaurant r : restaurants) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public List<Restaurant> findAll() {
        return restaurants;
    }
}
