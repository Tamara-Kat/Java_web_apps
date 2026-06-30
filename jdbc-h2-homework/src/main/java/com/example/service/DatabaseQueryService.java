package com.example.service;

import com.example.database.Database;
import com.example.model.*;
import com.example.util.FileUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {

    private final Connection connection = Database.getInstance().getConnection();

    public List<CategoryRevenue> findCategoryRevenue() {
        String sql = FileUtil.readFile("sql/find_category_revenue.sql");
        List<CategoryRevenue> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new CategoryRevenue(
                        rs.getString("CategoryName"),
                        rs.getInt("TotalQuantity"),
                        rs.getBigDecimal("TotalRevenue")
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find category revenue", e);
        }
    }

    public List<ClientRevenue> findClientRevenue() {
        String sql = FileUtil.readFile("sql/find_client_revenue.sql");
        List<ClientRevenue> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new ClientRevenue(
                        rs.getString("ClientName"),
                        rs.getInt("OrdersCount"),
                        rs.getInt("TotalUnits"),
                        rs.getBigDecimal("TotalRevenue")
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find client revenue", e);
        }
    }

    public List<ProductRevenue> findProductRevenue() {
        String sql = FileUtil.readFile("sql/find_product_revenue.sql");
        List<ProductRevenue> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new ProductRevenue(
                        rs.getString("ProductName"),
                        rs.getInt("TotalQuantity"),
                        rs.getBigDecimal("TotalRevenue")
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find product revenue", e);
        }
    }

    public List<StockReport> findStockReport() {
        String sql = FileUtil.readFile("sql/find_stock_report.sql");
        List<StockReport> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new StockReport(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getString("SKU"),
                        rs.getInt("CurrentStock"),
                        rs.getInt("MinStock")
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find stock report", e);
        }
    }

    public List<SalesReport> findSalesReport() {
        String sql = FileUtil.readFile("sql/find_sales_report.sql");
        List<SalesReport> result = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new SalesReport(
                        rs.getInt("OrderNumber"),
                        rs.getDate("OrderDate").toLocalDate(),
                        rs.getString("ClientName"),
                        rs.getString("Manager"),
                        rs.getString("ProductName"),
                        rs.getString("CategoryName"),
                        rs.getInt("Quantity"),
                        rs.getBigDecimal("UnitPrice"),
                        rs.getBigDecimal("LineTotal")
                ));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find sales report", e);
        }
    }
    public List<ProductRevenue> findProductsByCategoryId(int categoryId) {
        String sql = FileUtil.readFile("sql/find_products_by_category_id.sql");
        List<ProductRevenue> result = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, categoryId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(new ProductRevenue(
                            rs.getString("ProductName"),
                            rs.getInt("TotalQuantity"),
                            rs.getBigDecimal("TotalRevenue")
                    ));
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find products by category id", e);
        }
    }
    public static void main(String[] args) {
        DatabaseQueryService service = new DatabaseQueryService();

        System.out.println("Category Revenue:");
        service.findCategoryRevenue().forEach(System.out::println);

        System.out.println("\nClient Revenue:");
        service.findClientRevenue().forEach(System.out::println);

        System.out.println("\nProduct Revenue:");
        service.findProductRevenue().forEach(System.out::println);

        System.out.println("\nStock Report:");
        service.findStockReport().forEach(System.out::println);

        System.out.println("\nSales Report:");
        service.findSalesReport().forEach(System.out::println);

        System.out.println("\nProducts by Category ID = 1:");
        service.findProductsByCategoryId(1).forEach(System.out::println);
    }
}