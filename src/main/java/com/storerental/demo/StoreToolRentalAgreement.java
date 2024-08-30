package com.storerental.demo;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.*; 
import java.util.function.Supplier;


public class StoreToolRentalAgreement {
    private StoreTool tool;
    
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    
    private int chargeDays;
    private int rentalDays;
    private int discountPercent;
    
    private BigDecimal dailyRentalCharge;
    private BigDecimal preDiscountCharge;    
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;
    
    private Supplier<Stream<String>> agreementStreamSupplier;

	public StoreToolRentalAgreement(StoreTool tool, int rentalDays, LocalDate checkoutDate, int discountPercent) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.discountPercent = discountPercent;
        
        this.dueDate = checkoutDate.plusDays(rentalDays);
        this.dailyRentalCharge = tool.getType().getDailyCharge();
        
        prepareCharge();
        makeAgreementStringSupplier();
    }

    private void prepareCharge() {
        chargeDays = countChargeDays();
        preDiscountCharge = dailyRentalCharge.multiply(
        		BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP);
        discountAmount = preDiscountCharge.multiply(
        		BigDecimal.valueOf(discountPercent)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        finalCharge = preDiscountCharge.subtract(discountAmount);
    }

    private int countChargeDays() {
        int chargeDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);
        while (!currentDate.isAfter(dueDate)) {
            if (isChargeableDay(currentDate)) {
                chargeDays++;
            }
            currentDate = currentDate.plusDays(1);
        }
        return chargeDays;
    }

    private boolean isChargeableDay(LocalDate date) {
        if (isHoliday(date) && !tool.getType().isHolidayCharge() || 
        		(isWeekend(date) && !tool.getType().isWeekendCharge())) {
            return false;
        }
        return true;
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate date) {
        if (isIndependenceDay(date) || isLaborDay(date)) {
            return true;
        }
        return false;
    }

    private boolean isIndependenceDay(LocalDate date) {
        if (date.getMonth() == Month.JULY && date.getDayOfMonth() == 4) {
            return true;
        }
    	// take into account the 4th of July moving to Friday or Monday when it's on a weekend
        if (date.getMonth() == Month.JULY && date.getDayOfMonth() == 3 && date.getDayOfWeek() == DayOfWeek.FRIDAY) {
            return true;
        }
        if (date.getMonth() == Month.JULY && date.getDayOfMonth() == 5 && date.getDayOfWeek() == DayOfWeek.MONDAY) {
            return true;
        }
        return false;
    }

    private boolean isLaborDay(LocalDate date) {
        return (date.getDayOfMonth() <= 7 && 
        		date.getMonth() == Month.SEPTEMBER && 
        		date.getDayOfWeek() == DayOfWeek.MONDAY);
    }
    
    public void printAgreement() {
    	// print the the agreement line by line
        System.out.println();
        agreementStreamSupplier.get().forEach(System.out::println);
    }
    
    private void makeAgreementStringSupplier() {
    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    	agreementStreamSupplier = () -> Stream.of("Tool Code: " + tool.getCode(),
    			"Tool Type: " + tool.getType(),
    			"Tool Brand: " + tool.getBrand(),
    			"Rental Days: " + rentalDays,
    			"Check Out Date: " + checkoutDate.format(dateFormatter),
    			"Due Date: " + dueDate.format(dateFormatter),
    			"Daily Rental Charge: $" + String.format("%.2f", dailyRentalCharge),
    			"Charge Days: " + chargeDays,
    			"Pre-discount Charge: $" + String.format("%.2f", preDiscountCharge),
    			"Discount Percent: " + discountPercent + "%",
    			"Discount Amount: $" + String.format("%.2f", discountAmount),
    			"Final Charge: $" + String.format("%.2f", finalCharge)); 	
    }
    
    public String getAgreementString() {
    	// return the whole agreement as a String with HTML breaks
    	return agreementStreamSupplier.get().map(Object::toString).collect(Collectors.joining("<br>"));
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }
}
