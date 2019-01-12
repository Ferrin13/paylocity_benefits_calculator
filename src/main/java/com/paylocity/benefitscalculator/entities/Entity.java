package com.paylocity.benefitscalculator.entities;

public abstract class Entity {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
}
