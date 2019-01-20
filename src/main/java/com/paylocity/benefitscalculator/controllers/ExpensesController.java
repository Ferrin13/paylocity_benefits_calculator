package com.paylocity.benefitscalculator.controllers;

import com.paylocity.benefitscalculator.entities.SalarySummary;
import com.paylocity.benefitscalculator.models.EmployeeInfo;
import com.paylocity.benefitscalculator.utility.SalaryCalculator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/expenses")
public class ExpensesController {
    private EmployeeInfo employeeInfo;
    private SalaryCalculator salaryCalculator;

    public ExpensesController() {
        employeeInfo = EmployeeInfo.getInstance();
        salaryCalculator = SalaryCalculator.getInstance();
    }

    @GetMapping("/salary/summary")
    public SalarySummary getSalarySummary() {
        SalarySummary output = new SalarySummary();
        output.setSalary(salaryCalculator.getAllEmployeeSalaries());
        output.setAdjustedSalary(salaryCalculator.getAllEmployeeAdjustedSalaries());
        output.setDeductions(salaryCalculator.getAllEmployeeDeductions());
        return output;
    }



}
