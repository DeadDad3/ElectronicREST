package org.example.dao;

import org.example.entity.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
public class OrderDAOImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("vezumu10")
            .withInitScript("schema.sql");

    private OrderDAO orderDAO;

    @BeforeEach
    void secondSetUp() throws Exception {
        postgreSQLContainer.start();
        String url = postgreSQLContainer.getJdbcUrl();
        String username = postgreSQLContainer.getUsername();
        String password = postgreSQLContainer.getPassword();

        Connection testConnection = DriverManager.getConnection(url, username, password);
        DatabaseConnection.setMockConnection(testConnection); // Устанавливаем тестовое соединение
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.resetConnection(); // Сбрасываем тестовое соединение
        postgreSQLContainer.stop();
    }

    @Test
    void testAddOrder() {
        Order order = new Order();
        // Здесь должны быть установлены свойства объекта Order, если это требуется

        orderDAO.addOrder(order);

        Order fetched = orderDAO.getOrderById(order.getId());
        assertNotNull(fetched);
    }
}
