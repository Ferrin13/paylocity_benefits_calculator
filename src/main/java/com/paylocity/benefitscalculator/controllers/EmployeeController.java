package com.paylocity.benefitscalculator.controllers;

import com.paylocity.benefitscalculator.entities.Dependent;
import com.paylocity.benefitscalculator.entities.Employee;
import com.paylocity.benefitscalculator.models.EmployeeInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeInfo employeeInfo;

    public EmployeeController() {
        employeeInfo = EmployeeInfo.getInstance();
    }

    @GetMapping()
    public Employee[] listEmployees() {
        return employeeInfo.getEmployeeStream().toArray(Employee[]::new);
    }

    @PostMapping()
    public void addEmployee(@RequestBody Employee employee) {
        employeeInfo.addEmployee(employee);
    }

    @GetMapping("/{employeeId}")
    public Optional<Employee> getEmployee(@PathVariable int employeeId) {
        return employeeInfo.getEmployee(employeeId);
    }

    @PatchMapping("/{employeeId}")
    public void updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee) {
        employee.setId(employeeId); //Allows front end to not have to specify id in employee object
        employeeInfo.updateEmployee(employee);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable int employeeId) {
        employeeInfo.deleteEmployee(employeeId);
    }

    @GetMapping("/{employeeId}/dependent")
    public Dependent[] getDependents(@PathVariable int employeeId) {
        return getEmployeeOrThrow(employeeId).getDependents().getAll().toArray(Dependent[]::new);
    }

    @PostMapping("/{employeeId}/dependent")
    public void addDependent(@PathVariable int employeeId, @RequestBody Dependent dependent) {
        getEmployeeOrThrow(employeeId).getDependents().addEntity(dependent);
    }

    private Employee getEmployeeOrThrow(int employeeId) {
        Optional<Employee> employee = employeeInfo.getEmployee(employeeId);
        if(employee.isEmpty()) {
            throw new IllegalArgumentException("No employee exists with given ID");
        }
        return employee.get();
    }
}
