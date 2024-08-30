package com.storerental.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rental")
public class StoreRentalController {

    @Autowired
    private StoreRentalService rentalService;

    @RequestMapping("/checkout")
    public ResponseEntity<String> checkout(
            @RequestParam String toolCode,
            @RequestParam int rentalDays,
            @RequestParam int discountPercent,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkoutDate) {
        try {
            StoreToolRentalAgreement agreement = rentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);
            return ResponseEntity.ok().body("Your tool rental agreement is created successfully:<br>" + 
                    agreement.getAgreementString());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}