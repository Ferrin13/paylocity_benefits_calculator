package com.paylocity.benefitscalculator.utility;

import com.paylocity.benefitscalculator.entities.Employee;
import com.paylocity.benefitscalculator.entities.StartingLetterDiscount;
import com.paylocity.benefitscalculator.models.BenefitsInfo;
import com.paylocity.benefitscalculator.models.EmployeeInfo;
import com.paylocity.benefitscalculator.models.PaycheckInfo;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

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

    public Map<PayPeriodLength, BigDecimal> getEmployeeRawSalary(int employeeId) {
        Map<PayPeriodLength, BigDecimal> output = new HashMap<>();
        BigDecimal yearlySalary = getEmployeeYearlySalary();
        output.put(PayPeriodLength.YEARLY, yearlySalary);
        output.put(PayPeriodLength.MONTHLY, yearlySalary.divide(new BigDecimal(12), RoundingMode.DOWN));
        output.put(PayPeriodLength.SINGLE, paycheckInfo.getAmount());
        return output;
    }

    public Map<PayPeriodLength, BigDecimal> getEmployeeDeductions(int employeeId) {
        Employee employee = getEmployeeOrThrow(employeeId, employeeInfo);
        BigDecimal yearlyTotal = benefitsInfo.getYearlyEmployeeCost();
        BigDecimal yearlyDependentsCost = benefitsInfo.getYearlyDependentCost().multiply(new BigDecimal(employee.getDependents().getAll().count()));
        yearlyTotal = yearlyTotal.add(yearlyDependentsCost);

        Optional<Double> totalDiscountPercentageOption = benefitsInfo.getStartingLetterDiscounts().getAll()
                .filter(d -> d.getStartingLetter() == employee.getFirstName().toUpperCase().charAt(0))
                .map(StartingLetterDiscount::getDiscountPercentage)
                .reduce((a, b) -> a + b);

        BigDecimal totalDiscountPercentage = new BigDecimal(totalDiscountPercentageOption.orElse(0.0));
        yearlyTotal = yearlyTotal.multiply(BigDecimal.ONE.subtract(totalDiscountPercentage)).setScale(2, RoundingMode.HALF_EVEN);

        Map<PayPeriodLength, BigDecimal> output = new ConcurrentHashMap<>();
        output.put(PayPeriodLength.YEARLY, yearlyTotal);
        output.put(PayPeriodLength.MONTHLY, yearlyTotal.divide(new BigDecimal(12), RoundingMode.HALF_EVEN));
        output.put(PayPeriodLength.SINGLE, yearlyTotal.divide(new BigDecimal(paycheckInfo.getNumPerYear()), RoundingMode.HALF_EVEN));
        return output;
    }

    public Map<PayPeriodLength, BigDecimal> getEmployeeAdjustedSalary(int employeeId) {
        Employee employee = getEmployeeOrThrow(employeeId, employeeInfo);
        BigDecimal yearlySalary = getEmployeeYearlySalary();
        BigDecimal monthlySalary = yearlySalary.divide(new BigDecimal(12), RoundingMode.HALF_EVEN);
        Map<PayPeriodLength, BigDecimal> deductions = getEmployeeDeductions(employeeId);

        Map<PayPeriodLength, BigDecimal> output = new ConcurrentHashMap<>();
        output.put(PayPeriodLength.YEARLY, yearlySalary.subtract(deductions.get(PayPeriodLength.YEARLY)));
        output.put(PayPeriodLength.MONTHLY, monthlySalary.subtract(deductions.get(PayPeriodLength.MONTHLY)));
        output.put(PayPeriodLength.SINGLE, paycheckInfo.getAmount().subtract(deductions.get(PayPeriodLength.SINGLE)));
        return output;
    }

    public Map<PayPeriodLength, BigDecimal> getAllEmployeeSalaries() {
        Supplier<Stream<Map<PayPeriodLength, BigDecimal>>> mapStreamSupplier = () ->
                employeeInfo.getEmployeeStream().map(employee -> getEmployeeRawSalary(employee.getId()));
        return sumMapStream(mapStreamSupplier);
    }

    public Map<PayPeriodLength, BigDecimal> getAllEmployeeAdjustedSalaries() {
        Supplier<Stream<Map<PayPeriodLength, BigDecimal>>> mapStreamSupplier = () ->
                employeeInfo.getEmployeeStream().map(employee -> getEmployeeAdjustedSalary(employee.getId()));
        return sumMapStream(mapStreamSupplier);
    }

    public Map<PayPeriodLength, BigDecimal> getAllEmployeeDeductions() {
        Supplier<Stream<Map<PayPeriodLength, BigDecimal>>> mapStreamSupplier = () ->
                employeeInfo.getEmployeeStream().map(employee -> getEmployeeDeductions(employee.getId()));
        return sumMapStream(mapStreamSupplier);
    }

    private Map<PayPeriodLength, BigDecimal> sumMapStream(Supplier<Stream<Map<PayPeriodLength, BigDecimal>>> mapStreamSupplier) {
        Map<PayPeriodLength, BigDecimal> output = new HashMap<>();
        Arrays.stream(PayPeriodLength.values()).forEachOrdered(payPeriodLength ->
            output.put(payPeriodLength, mapStreamSupplier.get()
                    .map(map -> map.get(payPeriodLength))
                    .reduce(BigDecimal::add).orElse(new BigDecimal(0)))
        );
        return output;
    }

    private BigDecimal getEmployeeYearlySalary() {
        return paycheckInfo.getAmount().multiply(new BigDecimal(paycheckInfo.getNumPerYear()));
    }

    public enum PayPeriodLength {
        YEARLY,
        MONTHLY,
        SINGLE
    }
}
