package com.paylocity.benefitscalculator.entities;

public class Dependent extends Entity {

    private String firstName;
    private String lastName;
    private DependentType dependentType;

    public Dependent() {}

    public Dependent(String firstName, String lastName, DependentType dependentType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dependentType = dependentType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public DependentType getDependentType() {
        return dependentType;
    }

    public void setDependentType(DependentType dependentType) {
        this.dependentType = dependentType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public enum DependentType{
        SPOUSE,
        CHILD,
        OTHER
    }
}
