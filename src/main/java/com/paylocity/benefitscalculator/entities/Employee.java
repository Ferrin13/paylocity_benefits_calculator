package com.paylocity.benefitscalculator.entities;

import com.paylocity.benefitscalculator.utility.Repository;

public class Employee extends Entity {
    private String name;
    private Repository<Dependent> dependents;

    public Employee() {}

    public Employee(String name) {
        this.name = name;
        this.dependents = new Repository<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Repository<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(Repository<Dependent> dependents) {
        this.dependents = dependents;
    }

}
