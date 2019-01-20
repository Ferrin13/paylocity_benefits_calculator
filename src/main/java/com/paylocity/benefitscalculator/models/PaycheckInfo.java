package com.paylocity.benefitscalculator.models;

import java.math.BigDecimal;

public class PaycheckInfo {
    private static PaycheckInfo ourInstance = new PaycheckInfo();

    public static PaycheckInfo getInstance() {
        return ourInstance;
    }

    private BigDecimal amount;
    private int numPerYear;

    private PaycheckInfo() {
        //Default specification from problem description
        this.amount = new BigDecimal(2000);
        this.numPerYear = 26;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal setAmount(BigDecimal amount) {
        this.amount = amount;
        return this.getAmount();
    }

    public int getNumPerYear() {
        return this.numPerYear;
    }

    public int setNumPerYear(int numPerYear) {
        this.numPerYear = numPerYear;
        return this.getNumPerYear();
    }
}
