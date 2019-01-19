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
    public void setPaycheckAmount(@RequestBody BigDecimal paycheckAmount) {
        paycheckInfo.setAmount(paycheckAmount);
    }

    @GetMapping("/num-yearly")
    public int getNumYearlyPaychecks() {
        return paycheckInfo.getNumPerYear();
    }

    @PutMapping("/num-yearly")
    public void setNumYearlyPaychecks(@RequestBody int numYearlyPaychecks) {
        paycheckInfo.setNumPerYear(numYearlyPaychecks);
    }
}
