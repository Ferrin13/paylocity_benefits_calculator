package com.paylocity.benefitscalculator.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.paylocity.benefitscalculator.utility.Repository;

public class Employee extends Entity {
    private String firstName;
    private String lastName;
    private Repository<Dependent> dependents;

    public Employee() {}

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;

        this.lastName = lastName;
        this.dependents = new Repository<>();
    }

    public Repository<Dependent> getDependents() {
        return dependents;
    }

    public void setDependents(Repository<Dependent> dependents) {
        this.dependents = dependents;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
