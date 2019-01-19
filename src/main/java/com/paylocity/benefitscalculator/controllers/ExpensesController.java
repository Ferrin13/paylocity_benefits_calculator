package com.paylocity.benefitscalculator.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    @GetMapping("/salary/summary")
    public SalarySummary getSalarySummary() {
        return new SalarySummary();
    }

    private class SalarySummary {
        private
        SalarySummary() {}
    }

}
