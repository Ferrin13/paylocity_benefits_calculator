package com.paylocity.benefitscalculator.entities;

public class Dependent extends Entity {

    private String name;
    private DependentType dependentType;

    public Dependent() {}

    public Dependent(String name, DependentType dependentType) {
        this.name = name;
        this.dependentType = dependentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DependentType getDependentType() {
        return dependentType;
    }

    public void setDependentType(DependentType dependentType) {
        this.dependentType = dependentType;
    }

    public enum DependentType{
        SPOUSE,
        CHILD,
        OTHER
    }
}
