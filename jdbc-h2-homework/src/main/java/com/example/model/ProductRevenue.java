package com.example.model;

import java.math.BigDecimal;

public class ProductRevenue {
    private String productName;
    private int totalQuantity;
    private BigDecimal totalRevenue;

    public ProductRevenue(String productName, int totalQuantity, BigDecimal totalRevenue) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
    }

    @Override
    public String toString() {
        return "ProductRevenue{" +
                "productName='" + productName + '\'' +
                ", totalQuantity=" + totalQuantity +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}
