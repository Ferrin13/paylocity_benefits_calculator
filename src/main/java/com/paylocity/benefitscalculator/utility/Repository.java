package com.paylocity.benefitscalculator.utility;

import com.paylocity.benefitscalculator.entities.Employee;
import com.paylocity.benefitscalculator.entities.Entity;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class Repository<T extends Entity> {

    private int idCounter;
    private ArrayList<T> entityList;
    public Repository() {
        idCounter = 0;
        entityList = new ArrayList<>();
    }

    public void addEntity(T entity) {
        entity.setId(++this.idCounter);
        boolean idExists = entityList.stream().anyMatch(e -> e.getId() == entity.getId());
        if(idExists) {
            throw new IllegalStateException("Repository attempted to add an entity with an Id that already exists");
        }
        entityList.add(entity);
    }

    public Optional<T> getById(int id) {
        return entityList.stream().filter(e -> e.getId() == id).findFirst();
    }

    public void updateEntity(T entity) {
        boolean containsId = entityList.stream().anyMatch(e -> e.getId() == entity.getId());
        if(!containsId) {
            throw new IllegalArgumentException("No IDs match provided entity (Consider using addEntity)");
        }
        entityList.removeIf(e -> e.getId() == entity.getId());
        entityList.add(entity);
    }

    public void deleteById(int id) {
        entityList.removeIf(e -> e.getId() == id);
    }

    public Stream<T> getAll() {
        return entityList.stream();
    }
}
