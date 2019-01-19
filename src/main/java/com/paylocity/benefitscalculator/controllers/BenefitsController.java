package com.paylocity.benefitscalculator.controllers;

import com.paylocity.benefitscalculator.entities.StartingLetterDiscount;
import com.paylocity.benefitscalculator.models.BenefitsInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/benefits")
public class BenefitsController {
    private BenefitsInfo benefitsInfo = BenefitsInfo.getInstance();

    @GetMapping("/yearly/employee-cost")
    public BigDecimal getYearlyEmployeeCost() {
        return benefitsInfo.getYearlyEmployeeCost();
    }

    @PutMapping("/yearly/employee-cost")
    public void setYearlyEmployeeCost(@RequestBody BigDecimal yearlyEmployeeCost) {
        benefitsInfo.setYearlyEmployeeCost(yearlyEmployeeCost);
    }

    @GetMapping("/yearly/dependent-cost")
    public BigDecimal getYearlyDependentCost() {
        return benefitsInfo.getYearlyDependentCost();
    }

    @PutMapping("/yearly/dependent-cost")
    public void setYearlyDependentCost(@RequestBody BigDecimal yearlyDependentCost) {
        benefitsInfo.setYearlyDependentCost(yearlyDependentCost);
    }

    @GetMapping("/all/discounts/starting-letter")
    public StartingLetterDiscount[] getStartingLetterDiscounts() {
        return benefitsInfo.getStartingLetterDiscounts().getAll().toArray(StartingLetterDiscount[]::new);
    }

    @PostMapping("/all/discounts/starting-letter")
    public void addStartingLetterDiscount(@RequestBody StartingLetterDiscount discount) {
        benefitsInfo.addStartingLetterDiscount(discount);
    }

    @GetMapping("/all/discounts/starting-letter/{discountId}")
    public StartingLetterDiscount getStartingLetterDiscount(@PathVariable int discountId) {
        Optional<StartingLetterDiscount> discountOptional = benefitsInfo.getStartingLetterDiscounts().getById(discountId);
        if(discountOptional.isEmpty()) {
            throw new IllegalArgumentException("No discount with given Id exists");
        }
        return discountOptional.get();
    }

    @PatchMapping("/all/discounts/starting-letter/{discountId}")
    public void updateStartingLetterDiscount(@PathVariable int discountId, @RequestBody StartingLetterDiscount discount) {
        discount.setId(discountId); //Allows front end to not have to set id on discount object
        benefitsInfo.getStartingLetterDiscounts().updateEntity(discount);
    }

    @DeleteMapping("/all/discounts/starting-letter/{discountId}")
    public void deleteStartingLetterDiscount(@PathVariable int discountId) {
        benefitsInfo.removeStartingLetterDiscount(discountId);
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
