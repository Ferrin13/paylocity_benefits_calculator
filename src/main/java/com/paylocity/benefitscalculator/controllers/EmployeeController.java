package com.paylocity.benefitscalculator.controllers;

import com.paylocity.benefitscalculator.entities.Dependent;
import com.paylocity.benefitscalculator.entities.Employee;
import com.paylocity.benefitscalculator.entities.SalarySummary;
import com.paylocity.benefitscalculator.models.EmployeeInfo;
import com.paylocity.benefitscalculator.utility.SalaryCalculator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static com.paylocity.benefitscalculator.utility.Utility.getEmployeeOrThrow;

/*
    Currently, all post handlers allow for nulls to be passed, even for required fields.'
    This could/should be fixed with custom serializers for each class.
*/



@CrossOrigin()
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeInfo employeeInfo;
    private SalaryCalculator salaryCalculator;

    public EmployeeController() {
        employeeInfo = EmployeeInfo.getInstance();
        salaryCalculator = SalaryCalculator.getInstance();
    }

    @GetMapping()
    public Employee[] listEmployees() {
        return employeeInfo.getEmployeeStream().toArray(Employee[]::new);
    }

    @PostMapping()
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeInfo.addEmployee(employee);
    }

    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        return getEmployeeOrThrow(employeeId, employeeInfo);
    }

    @PatchMapping("/{employeeId}")
    public Employee updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee) {
        employee.setId(employeeId); //Allows front end to not have to specify id in employee object
        return employeeInfo.updateEmployee(employee);
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable int employeeId) {
        employeeInfo.deleteEmployee(employeeId);
    }

    @GetMapping("/{employeeId}/dependent")
    public Dependent[] getDependents(@PathVariable int employeeId) {
        return getEmployeeOrThrow(employeeId, employeeInfo).getDependents().getAll().toArray(Dependent[]::new);
    }

    @PostMapping("/{employeeId}/dependent")
    public Dependent addDependent(@PathVariable int employeeId, @RequestBody Dependent dependent) {
        return getEmployeeOrThrow(employeeId, employeeInfo).getDependents().addEntity(dependent);
    }

    @GetMapping("/{employeeId}/dependent/{dependentId}")
    public Dependent getDependent(@PathVariable int employeeId, @PathVariable int dependentId) {
        Optional<Dependent> dependent = getEmployeeOrThrow(employeeId, employeeInfo).getDependents().getById(dependentId);
        if(dependent.isEmpty()) {
            throw new IllegalArgumentException("No dependent exists with given ID");
        }
        return dependent.get();
    }

    @PatchMapping("/{employeeId}/dependent/{dependentId}")
    public void updateDependent(@PathVariable int employeeId, @PathVariable int dependentId, @RequestBody Dependent dependent) {
        dependent.setId(dependentId); //Allows front end to not have to specify id in dependent object
        getEmployeeOrThrow(employeeId, employeeInfo).getDependents().updateEntity(dependent);
    }

    @DeleteMapping("/{employeeId}/dependent/{dependentId}")
    public void deleteDependent(@PathVariable int employeeId, @PathVariable int dependentId) {
        getEmployeeOrThrow(employeeId, employeeInfo).getDependents().deleteById(dependentId);
    }

    @GetMapping("/{employeeId}/salary/summary")
    public SalarySummary getSalary(@PathVariable int employeeId) {
        SalarySummary output = new SalarySummary();
        output.setSalary(salaryCalculator.getEmployeeRawSalary(employeeId));
        output.setAdjustedSalary(salaryCalculator.getEmployeeAdjustedSalary(employeeId));
        output.setDeductions(salaryCalculator.getEmployeeDeductions(employeeId));
        return output;
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}