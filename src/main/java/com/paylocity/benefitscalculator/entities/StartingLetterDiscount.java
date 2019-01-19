package com.paylocity.benefitscalculator.entities;

public class StartingLetterDiscount extends Entity {
    private char startingLetter;
    private double discountPercentage;

    public StartingLetterDiscount() {}

    public StartingLetterDiscount(char startingLetter, double discountPercentage) {
        this.startingLetter = startingLetter;
        this.discountPercentage = discountPercentage;
    }

    public char getStartingLetter() {
        return startingLetter;
    }

    public void setStartingLetter(char startingLetter) {
        this.startingLetter = startingLetter;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
