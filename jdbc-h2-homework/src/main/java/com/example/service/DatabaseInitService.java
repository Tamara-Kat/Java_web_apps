package com.example.service;

import com.example.database.Database;
import com.example.util.FileUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitService {

    public static void main(String[] args) {
        String sql = FileUtil.readFile("sql/init_db.sql");

        try {
            Connection connection = Database.getInstance().getConnection();
            Statement statement = connection.createStatement();

            statement.execute(sql);

            System.out.println("Database structure was initialized successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}