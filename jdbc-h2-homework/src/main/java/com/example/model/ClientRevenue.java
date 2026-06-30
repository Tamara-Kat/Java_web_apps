package com.example.model;

import java.math.BigDecimal;

public class ClientRevenue {
    private String clientName;
    private int ordersCount;
    private int totalUnits;
    private BigDecimal totalRevenue;

    public ClientRevenue(String clientName, int ordersCount, int totalUnits, BigDecimal totalRevenue) {
        this.clientName = clientName;
        this.ordersCount = ordersCount;
        this.totalUnits = totalUnits;
        this.totalRevenue = totalRevenue;
    }

    @Override
    public String toString() {
        return "ClientRevenue{" +
                "clientName='" + clientName + '\'' +
                ", ordersCount=" + ordersCount +
                ", totalUnits=" + totalUnits +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}
