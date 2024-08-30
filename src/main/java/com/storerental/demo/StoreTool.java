package com.storerental.demo;

// POJO for a tool in a Home Depot like store
public class StoreTool {
	
	private String code;
    private StoreToolType type;
    private String brand;

    public StoreTool(String code, StoreToolType type, String brand) {
        this.code = code;
        this.type = type;
        this.brand = brand;
    }

    public String getCode() {
        return code;
    }

    public StoreToolType getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }
}
