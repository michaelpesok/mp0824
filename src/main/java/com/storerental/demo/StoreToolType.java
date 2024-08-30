package com.storerental.demo;

import java.math.BigDecimal;

enum StoreToolType {
	// currently supporting 3 types of tools
    LADDER(new BigDecimal("1.99"), true, true, false),
    CHAINSAW(new BigDecimal("1.49"), true, false, true),
    JACKHAMMER(new BigDecimal("2.99"), true, false, false);

    private final BigDecimal dailyCharge;
    private final boolean isWeekdayCharge;
    private final boolean isWeekendCharge;
    private final boolean isHolidayCharge;

    StoreToolType(BigDecimal dailyCharge, boolean isWeekdayCharge, boolean isWeekendCharge, boolean isHolidayCharge) {
        this.dailyCharge = dailyCharge;
        this.isWeekdayCharge = isWeekdayCharge;
        this.isWeekendCharge = isWeekendCharge;
        this.isHolidayCharge = isHolidayCharge;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public boolean isWeekdayCharge() {
        return isWeekdayCharge;
    }

    public boolean isWeekendCharge() {
        return isWeekendCharge;
    }

    public boolean isHolidayCharge() {
        return isHolidayCharge;
    }
}
