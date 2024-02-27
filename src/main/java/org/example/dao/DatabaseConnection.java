package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Здесь можно определить поля для соединения с базой данных,
    // такие как URL, имя пользователя и пароль.

    private static final String URL = "jdbc:postgresql://localhost:5432/ElectronicThings";
    private static final String USER = "postgres";
    private static final String PASSWORD = "vezumu10";

    // Метод для получения соединения с базой данных
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Дополнительные методы для работы с базой данных могут быть добавлены здесь
}