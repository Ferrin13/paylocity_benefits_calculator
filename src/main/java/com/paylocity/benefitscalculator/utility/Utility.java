package com.paylocity.benefitscalculator.utility;

import com.paylocity.benefitscalculator.entities.Employee;
import com.paylocity.benefitscalculator.models.EmployeeInfo;

import java.util.Optional;

public class Utility {
    public static Employee getEmployeeOrThrow(int employeeId, EmployeeInfo employeeInfo) {
        Optional<Employee> employee = employeeInfo.getEmployee(employeeId);
        if(employee.isEmpty()) {
            throw new IllegalArgumentException("No employee exists with given ID");
        }
        return employee.get();
    }
}
