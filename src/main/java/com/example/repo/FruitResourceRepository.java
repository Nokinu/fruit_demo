package com.example.repo;

import com.example.model.Fruit;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FruitResourceRepository implements PanacheRepository<Fruit> {

    public List<Fruit> findBySeason(String season) {
        return find("season", season).list();
    }
    public List<Fruit> findByName(String name) {
        return find("name", name).list();
    }
}
