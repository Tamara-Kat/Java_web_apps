package com.example.service;

import com.example.database.Database;
import com.example.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private final Connection connection = Database.getInstance().getConnection();

    private void validateName(String name) {
        if (name == null || name.trim().length() < 2 || name.length() > 100) {
            throw new IllegalArgumentException(
                    "Client name must contain from 2 to 100 characters."
            );
        }
    }
    public long create(String name) {
        validateName(name);

        String sql = "INSERT INTO Clients (ClientName) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {
            statement.setString(1, name.trim());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }

            throw new RuntimeException("Failed to create client. No ID returned.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create client", e);
        }
    }
    public String getById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Client ID must be positive.");
        }

        String sql = "SELECT ClientName FROM Clients WHERE ClientID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("ClientName");
                }
            }

            throw new IllegalArgumentException("Client with ID " + id + " not found.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get client by id", e);
        }
    }
    public void setName(long id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("Client ID must be positive.");
        }

        validateName(name);

        String sql = "UPDATE Clients SET ClientName = ? WHERE ClientID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name.trim());
            statement.setLong(2, id);

            int updatedRows = statement.executeUpdate();

            if (updatedRows == 0) {
                throw new IllegalArgumentException("Client with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update client name", e);
        }
    }
    public void deleteById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Client ID must be positive.");
        }

        String sql = "DELETE FROM Clients WHERE ClientID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            int deletedRows = statement.executeUpdate();

            if (deletedRows == 0) {
                throw new IllegalArgumentException("Client with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete client", e);
        }
    }
    public List<Client> listAll() {
        String sql = "SELECT ClientID, ClientName FROM Clients ORDER BY ClientID";
        List<Client> clients = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                clients.add(new Client(
                        rs.getLong("ClientID"),
                        rs.getString("ClientName")
                ));
            }

            return clients;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list clients", e);
        }
    }
    public static void main(String[] args) {
        ClientService service = new ClientService();

        System.out.println("All clients:");
        service.listAll().forEach(System.out::println);

        long newClientId = service.create("Test Client");
        System.out.println("\nCreated client ID: " + newClientId);

        System.out.println("Client by ID:");
        System.out.println(service.getById(newClientId));

        service.setName(newClientId, "Updated Test Client");
        System.out.println("Updated client:");
        System.out.println(service.getById(newClientId));

        service.deleteById(newClientId);
        System.out.println("Client deleted.");

        System.out.println("\nAll clients after delete:");
        service.listAll().forEach(System.out::println);
    }
}
