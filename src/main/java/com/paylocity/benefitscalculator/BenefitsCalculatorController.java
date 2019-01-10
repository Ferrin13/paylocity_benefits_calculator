package com.paylocity.benefitscalculator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BenefitsCalculatorController {

    @RequestMapping("/test")
    public String string(@RequestParam(value="echo", defaultValue = "Test Works") String echoValue) {
        return echoValue;
    }
}
