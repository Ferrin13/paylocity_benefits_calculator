package com.paylocity.benefitscalculator.entities;

import com.paylocity.benefitscalculator.utility.SalaryCalculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SalarySummary {
    private Map<SalaryCalculator.PayPeriodLength, BigDecimal> salary;
    private Map<SalaryCalculator.PayPeriodLength, BigDecimal> adjustedSalary;
    private Map<SalaryCalculator.PayPeriodLength, BigDecimal> deductions;

    public SalarySummary() {
        salary = new HashMap<>();
        adjustedSalary = new HashMap<>();
        deductions = new HashMap<>();
    }

    public Map<SalaryCalculator.PayPeriodLength, BigDecimal> getSalary() {
        return salary;
    }

    public void setSalary(Map<SalaryCalculator.PayPeriodLength, BigDecimal> salary) {
        this.salary = salary;
    }

    public Map<SalaryCalculator.PayPeriodLength, BigDecimal> getAdjustedSalary() {
        return adjustedSalary;
    }

    public void setAdjustedSalary(Map<SalaryCalculator.PayPeriodLength, BigDecimal> adjustedSalary) {
        this.adjustedSalary = adjustedSalary;
    }

    public Map<SalaryCalculator.PayPeriodLength, BigDecimal> getDeductions() {
        return deductions;
    }

    public void setDeductions(Map<SalaryCalculator.PayPeriodLength, BigDecimal> deductions) {
        this.deductions = deductions;
    }



}