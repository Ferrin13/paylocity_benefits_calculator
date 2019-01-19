package com.paylocity.benefitscalculator.utility;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.paylocity.benefitscalculator.entities.Entity;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

@JsonDeserialize(using = RepositoryDeserializer.class)
@JsonSerialize(using = RepositorySerializer.class)
public class Repository<T extends Entity> {

    private int idCounter;
    private ArrayList<T> entityList;
    public Repository() {
        idCounter = 0;
        entityList = new ArrayList<>();
    }

    public T addEntity(T entity) {
        entity.setId(++this.idCounter);
        boolean idExists = entityList.stream().anyMatch(e -> e.getId() == entity.getId());
        if(idExists) {
            throw new IllegalStateException("Repository attempted to add an entity with an Id that already exists");
        }
        entityList.add(entity);
        return entity;
    }

    public Optional<T> getById(int id) {
        return entityList.stream().filter(e -> e.getId() == id).findFirst();
    }

    public T updateEntity(T entity) {
        boolean containsId = entityList.stream().anyMatch(e -> e.getId() == entity.getId());
        if(!containsId) {
            throw new IllegalArgumentException("No IDs match provided entity (Consider using addEntity)");
        }
        entityList.removeIf(e -> e.getId() == entity.getId());
        entityList.add(entity);
        return entity;
    }

    public void deleteById(int id) {
        entityList.removeIf(e -> e.getId() == id);
    }

    public Stream<T> getAll() {
        return entityList.stream();
    }
}

