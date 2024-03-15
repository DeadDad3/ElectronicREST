package org.example.dao;

import org.example.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ProductDAOImpl productDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        // Мокируем ответы resultSet
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Test Product");
        when(resultSet.getString("description")).thenReturn("This is a test product");
        when(resultSet.getDouble("price")).thenReturn(9.99);
    }

    @Test
    public void getProductByIdTest() throws Exception {
        Product product = productDAO.getProductById(1);

        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("This is a test product", product.getDescription());
        assertEquals(9.99, product.getPrice(), 0.001);
    }

    @Test
    public void addProductTest() throws Exception {
        Product product = new Product();
        product.setName("New Product");
        product.setDescription("New Description");
        product.setPrice(100.00);
        product.setCategoryId(1);

        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(10); // Предположим, что новому продукту был присвоен ID 10

        productDAO.addProduct(product);

        verify(preparedStatement, times(1)).setString(1, product.getName());
        verify(preparedStatement, times(1)).setString(2, product.getDescription());
        verify(preparedStatement, times(1)).setDouble(3, product.getPrice());
        verify(preparedStatement, times(1)).executeUpdate();

        assertEquals(10, product.getId()); // Проверяем, что ID продукта был обновлен
    }

    @Test
    public void updateProductTest() throws SQLException {
        Product product = new Product();
        product.setId(1); // Предполагаем, что продукт с таким ID существует
        product.setName("Updated Product");
        product.setDescription("Updated Description");
        product.setPrice(200.0);
        product.setCategoryId(2); // Предполагаем, что категория с таким ID существует

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        productDAO.updateProduct(product);

        verify(preparedStatement, times(1)).setString(1, product.getName());
        verify(preparedStatement, times(1)).setString(2, product.getDescription());
        verify(preparedStatement, times(1)).setDouble(3, product.getPrice());
        verify(preparedStatement, times(1)).setInt(4, product.getCategoryId());
        verify(preparedStatement, times(1)).setInt(5, product.getId());
        verify(preparedStatement, times(1)).executeUpdate();
    }


    @Test
    public void deleteProductTest() throws SQLException {
        int productId = 1; // Предполагаем, что продукт с таким ID существует

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        productDAO.deleteProduct(productId);

        verify(preparedStatement, times(1)).setInt(1, productId);
        verify(preparedStatement, times(1)).executeUpdate();
    }
}
