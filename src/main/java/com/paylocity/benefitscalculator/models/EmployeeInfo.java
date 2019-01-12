package com.paylocity.benefitscalculator.models;

import com.paylocity.benefitscalculator.entities.Employee;
import com.paylocity.benefitscalculator.utility.Repository;

import java.util.Optional;
import java.util.stream.Stream;

public class EmployeeInfo {
    private static EmployeeInfo ourInstance = new EmployeeInfo();

    public static EmployeeInfo getInstance() {
        return ourInstance;
    }

    private Repository<Employee> employeeRepository;

    private EmployeeInfo() {
        employeeRepository = new Repository<>();
    }

    public void addEmployee(Employee employee) {
        employeeRepository.addEntity(employee);
    }

    public Optional<Employee> getEmployee(int employeeId) {
        return employeeRepository.getById(employeeId);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.updateEntity(employee);
    }

    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public Stream<Employee> getEmployeeStream() {
        return employeeRepository.getAll();
    }
}
