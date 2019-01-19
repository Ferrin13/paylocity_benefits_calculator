package com.paylocity.benefitscalculator.utility;

import com.paylocity.benefitscalculator.entities.Employee;
import com.paylocity.benefitscalculator.entities.StartingLetterDiscount;
import com.paylocity.benefitscalculator.models.BenefitsInfo;
import com.paylocity.benefitscalculator.models.EmployeeInfo;
import com.paylocity.benefitscalculator.models.PaycheckInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.DoubleSupplier;

import static com.paylocity.benefitscalculator.utility.Utility.getEmployeeOrThrow;

public class SalaryCalculator {
    private static SalaryCalculator ourInstance = new SalaryCalculator();

    public static SalaryCalculator getInstance() {
        return ourInstance;
    }

    private BenefitsInfo benefitsInfo;
    private EmployeeInfo employeeInfo;
    private PaycheckInfo paycheckInfo;

    private SalaryCalculator() {
        benefitsInfo = BenefitsInfo.getInstance();
        employeeInfo = EmployeeInfo.getInstance();
        paycheckInfo = PaycheckInfo.getInstance();
    }

    public Map<PayPeriodLength, BigDecimal> getEmployeeDeductions(int employeeId) {
        Employee employee = getEmployeeOrThrow(employeeId, employeeInfo);
        BigDecimal yearlyTotal = benefitsInfo.getYearlyEmployeeCost();
        BigDecimal yearlyDependentsCost = benefitsInfo.getYearlyDependentCost().multiply(new BigDecimal(employee.getDependents().getAll().count()));
        yearlyTotal = yearlyTotal.add(yearlyDependentsCost);

        Optional<Double> totalDiscountPercentageOption = benefitsInfo.getStartingLetterDiscounts().getAll()
                .filter(d -> d.getStartingLetter() == employee.getFirstName().toLowerCase().charAt(0))
                .map(StartingLetterDiscount::getDiscountPercentage)
                .reduce((a, b) -> a + b);

        BigDecimal totalDiscountPercentage = new BigDecimal(totalDiscountPercentageOption.orElse(0.0));
        yearlyTotal = yearlyTotal.multiply(BigDecimal.ONE.subtract(totalDiscountPercentage));

        Map<PayPeriodLength, BigDecimal> output = new HashMap<>();
        output.put(PayPeriodLength.YEARLY, yearlyTotal);
        output.put(PayPeriodLength.MONTHLY, yearlyTotal.divide(new BigDecimal(12)));
        output.put(PayPeriodLength.SINGLE, paycheckInfo.getAmount());
        return output;
    }

    public Map<PayPeriodLength, BigDecimal> getEmployeeAdjustedSalary(int employeeId) {
        Employee employee = getEmployeeOrThrow(employeeId, employeeInfo);
        BigDecimal yearlySalary = paycheckInfo.getAmount().multiply(new BigDecimal(paycheckInfo.getNumPerYear()));
        BigDecimal monthlySalary = yearlySalary.divide(new BigDecimal(12));
        Map<PayPeriodLength, BigDecimal> deductions = getEmployeeDeductions(employeeId);

        Map<PayPeriodLength, BigDecimal> output = new HashMap<>();
        output.put(PayPeriodLength.YEARLY, yearlySalary.subtract(deductions.get(PayPeriodLength.YEARLY)));
        output.put(PayPeriodLength.MONTHLY, monthlySalary.subtract(deductions.get(PayPeriodLength.MONTHLY)));
        output.put(PayPeriodLength.SINGLE, paycheckInfo.getAmount().subtract(deductions.get(PayPeriodLength.SINGLE)));
        return output;
    }

    public enum PayPeriodLength {
        YEARLY,
        MONTHLY,
        SINGLE
    }
}
