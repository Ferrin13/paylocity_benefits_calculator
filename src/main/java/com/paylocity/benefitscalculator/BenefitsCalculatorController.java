package com.paylocity.benefitscalculator;

import com.paylocity.benefitscalculator.entities.Employee;
import org.springframework.web.bind.annotation.*;


@RestController
public class BenefitsCalculatorController {

//    @CrossOrigin(origins = "http://localhost:7070")
    @CrossOrigin()
    @RequestMapping("/test")
    public Employee test(@RequestParam(value="echo", defaultValue = "Test Works") String echoValue) {
        return new Employee(echoValue + echoValue);
    }
}
