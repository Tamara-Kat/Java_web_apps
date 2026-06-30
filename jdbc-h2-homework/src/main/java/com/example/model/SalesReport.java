package com.example.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesReport {
    private int orderNumber;
    private LocalDate orderDate;
    private String clientName;
    private String manager;
    private String productName;
    private String categoryName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;

    public SalesReport(
            int orderNumber,
            LocalDate orderDate,
            String clientName,
            String manager,
            String productName,
            String categoryName,
            int quantity,
            BigDecimal unitPrice,
            BigDecimal lineTotal
    ) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.clientName = clientName;
        this.manager = manager;
        this.productName = productName;
        this.categoryName = categoryName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    @Override
    public String toString() {
        return "SalesReport{" +
                "orderNumber=" + orderNumber +
                ", orderDate=" + orderDate +
                ", clientName='" + clientName + '\'' +
                ", manager='" + manager + '\'' +
                ", productName='" + productName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", lineTotal=" + lineTotal +
                '}';
    }
}