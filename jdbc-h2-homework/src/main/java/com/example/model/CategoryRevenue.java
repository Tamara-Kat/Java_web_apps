package com.example.model;

import java.math.BigDecimal;

public class CategoryRevenue {
    private String categoryName;
    private int totalQuantity;
    private BigDecimal totalRevenue;

    public CategoryRevenue(String categoryName, int totalQuantity, BigDecimal totalRevenue) {
        this.categoryName = categoryName;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
    }

    @Override
    public String toString() {
        return "CategoryRevenue{" +
                "categoryName='" + categoryName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}
