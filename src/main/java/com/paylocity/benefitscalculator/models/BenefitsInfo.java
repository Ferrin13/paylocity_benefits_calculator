package com.paylocity.benefitscalculator.models;

import com.paylocity.benefitscalculator.entities.StartingLetterDiscount;
import com.paylocity.benefitscalculator.utility.Repository;

import java.math.BigDecimal;

public class BenefitsInfo {
    private static BenefitsInfo ourInstance = new BenefitsInfo();

    public static BenefitsInfo getInstance() {
        return ourInstance;
    }

    private BigDecimal yearlyEmployeeCost;
    private BigDecimal yearlyDependentCost;
    private Repository<StartingLetterDiscount> startingLetterDiscounts;

    private BenefitsInfo() {
        //Defaults specified by problem description
        yearlyEmployeeCost = new BigDecimal(1000);
        yearlyDependentCost = new BigDecimal(500);
        startingLetterDiscounts = new Repository<>();
        startingLetterDiscounts.addEntity(new StartingLetterDiscount('A', .10));
    }

    public BigDecimal getYearlyDependentCost() {
        return yearlyDependentCost;
    }

    public void setYearlyDependentCost(BigDecimal yearlyDependentCost) {
        this.yearlyDependentCost = yearlyDependentCost;
    }

    public BigDecimal getYearlyEmployeeCost() {
        return yearlyEmployeeCost;
    }

    public void setYearlyEmployeeCost(BigDecimal yearlyEmployeeCost) {
        this.yearlyEmployeeCost = yearlyEmployeeCost;
    }

    public Repository<StartingLetterDiscount> getStartingLetterDiscounts() {
        return startingLetterDiscounts;
    }

    public void addStartingLetterDiscount(StartingLetterDiscount startingLetterDiscount) {
        this.startingLetterDiscounts.addEntity(startingLetterDiscount);
    }

    public void removeStartingLetterDiscount(int discountId) {
        this.startingLetterDiscounts.deleteById(discountId);
    }
}
