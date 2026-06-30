package com.example.service;

import com.example.database.Database;
import com.example.util.FileUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePopulateService {

    public static void main(String[] args) {
        String sql = FileUtil.readFile("sql/populate_db.sql");

        try {
            Connection connection = Database.getInstance().getConnection();
            Statement statement = connection.createStatement();

            statement.execute(sql);

            System.out.println("Database was populated successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to populate database", e);
        }
    }
}
