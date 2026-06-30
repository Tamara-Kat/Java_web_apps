package com.example.model;

public class StockReport {
    private int productId;
    private String productName;
    private String sku;
    private int currentStock;
    private int minStock;

    public StockReport(int productId, String productName, String sku, int currentStock, int minStock) {
        this.productId = productId;
        this.productName = productName;
        this.sku = sku;
        this.currentStock = currentStock;
        this.minStock = minStock;
    }

    @Override
    public String toString() {
        return "StockReport{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", sku='" + sku + '\'' +
                ", currentStock=" + currentStock +
                ", minStock=" + minStock +
                '}';
    }
}
