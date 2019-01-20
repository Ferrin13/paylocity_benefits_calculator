package com.paylocity.benefitscalculator.controllers;

import com.paylocity.benefitscalculator.models.PaycheckInfo;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin()
@RestController
@RequestMapping("/paycheck")
public class PaycheckController {
    PaycheckInfo paycheckInfo = PaycheckInfo.getInstance();

    @GetMapping("/amount")
    public BigDecimal getPaycheckAmount() {
        return paycheckInfo.getAmount();
    }

    @PutMapping("/amount")
    public BigDecimal setPaycheckAmount(@RequestBody BigDecimal paycheckAmount) {
        return paycheckInfo.setAmount(paycheckAmount);
    }

    @GetMapping("/num-yearly")
    public int getNumYearlyPaychecks() {
        return paycheckInfo.getNumPerYear();
    }

    @PutMapping("/num-yearly")
    public int setNumYearlyPaychecks(@RequestBody int numYearlyPaychecks) {
        return paycheckInfo.setNumPerYear(numYearlyPaychecks);
    }
}
