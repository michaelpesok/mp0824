package com.storerental.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class StoreToolRentalApplicationTests {

    private StoreRentalService rentalService;

    @BeforeEach
    void setUp() {
        rentalService = new StoreRentalService();
    }

    @Test
    void testInvalidDiscountPercent() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        });
        assertEquals("Discount percent must be in the range of 0 - 100", exception.getMessage());
    }

    @Test
    void testLadderRental() {
        StoreToolRentalAgreement agreement = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        agreement.printAgreement();
        assertEquals(1, agreement.getChargeDays());
        assertEquals(new BigDecimal("1.79"), agreement.getFinalCharge());
    }

    @Test
    void testChainSawRental() {
        StoreToolRentalAgreement agreement = rentalService.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
        agreement.printAgreement();
        assertEquals(3, agreement.getChargeDays());
        assertEquals(new BigDecimal("3.35"), agreement.getFinalCharge());
    }

    @Test
    void testJackHammerRentalDeWalt() {
        StoreToolRentalAgreement agreement = rentalService.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        agreement.printAgreement();
        assertEquals(3, agreement.getChargeDays());
        assertEquals(new BigDecimal("8.97"), agreement.getFinalCharge());
    }

    @Test
    void testJackHammerRentalRidgid9Days() {
        StoreToolRentalAgreement agreement = rentalService.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        agreement.printAgreement();
        assertEquals(5, agreement.getChargeDays());
        assertEquals(new BigDecimal("14.95"), agreement.getFinalCharge());
    }

    @Test
    void testJackHammerRentalRidgid4Days() {
        StoreToolRentalAgreement agreement = rentalService.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
        agreement.printAgreement();
        assertEquals(1, agreement.getChargeDays());
        assertEquals(new BigDecimal("1.49"), agreement.getFinalCharge());
    }
}
