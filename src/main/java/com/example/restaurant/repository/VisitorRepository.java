package com.example.restaurant.repository;

import com.example.restaurant.entity.Visitor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VisitorRepository {

    private final List<Visitor> visitors = new ArrayList<>();

    public void save(Visitor visitor) {
        for (int i = 0; i < visitors.size(); i++) {
            if (visitors.get(i).getId().equals(visitor.getId())) {
                visitors.set(i, visitor);
                return;
            }
        }
        visitors.add(visitor);
    }

    public void removeById(Long id) {
        visitors.removeIf(v -> v.getId().equals(id));
    }

    public Visitor findById(Long id) {
        for (Visitor v : visitors) {
            if (v.getId().equals(id)) {
                return v;
            }
        }
        return null;
    }

    public List<Visitor> findAll() {
        return visitors;
    }
}
