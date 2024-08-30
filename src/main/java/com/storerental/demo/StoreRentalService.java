package com.storerental.demo;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class StoreRentalService {
    public StoreToolRentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Tool rental day count must be 1 or greater");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be in the range of 0 - 100");
        }

        StoreTool tool = getToolByCode(toolCode);
        return new StoreToolRentalAgreement(tool, rentalDays, checkoutDate, discountPercent);
    }
    
    private StoreTool getToolByCode(String code) {
        switch (code) {
            case "CHNS":
                return new StoreTool("CHNS", StoreToolType.CHAINSAW, "Stihl");
            case "LADW":
                return new StoreTool("LADW", StoreToolType.LADDER, "Werner");
            case "JAKD":
                return new StoreTool("JAKD", StoreToolType.JACKHAMMER, "DeWalt");
            case "JAKR":
                return new StoreTool("JAKR", StoreToolType.JACKHAMMER, "Ridgid");
            default:
                throw new IllegalArgumentException("Invalid tool code: " + code);
        }
    }
}
