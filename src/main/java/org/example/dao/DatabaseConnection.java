package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            String URL = "jdbc:postgresql://localhost:5432/ElectronicThings";
            String USER = "postgres";
            String PASSWORD = "vezumu10";
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    // Используй этот метод в тестах для установки мок-соединения
    public static void setMockConnection(Connection mockConnection) {
        connection = mockConnection;
    }

    // Добавь этот метод для сброса состояния между тестами
    public static void resetConnection() {
        connection = null;
    }
}
