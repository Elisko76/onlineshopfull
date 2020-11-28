package com.onlineshop.model;

public enum ProductCategory {
    PHONE("Phone"), TABLET("Tablet"), EBOOK("E-Book"), WATCH("Watch"), HEADPHONES("Headphones");
    private final String displayValue;
    
    ProductCategory(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return this.displayValue;
    }
    
}
