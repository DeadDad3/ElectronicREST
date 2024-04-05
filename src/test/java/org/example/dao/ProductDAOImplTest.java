package org.example.dao;

import org.example.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductDAOImplTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        DatabaseConnection.setMockConnection(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        lenient().when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        lenient().when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        lenient().when(mockResultSet.getInt("id")).thenReturn(1);
        lenient().when(mockResultSet.getString("name")).thenReturn("Test Product");
        lenient().when(mockResultSet.getString("description")).thenReturn("This is a test product");
        lenient().when(mockResultSet.getDouble("price")).thenReturn(9.99);
    }

    @Test
     void getProductByIdTest() throws Exception {
        ProductDAOImpl dao = new ProductDAOImpl();
        Product product = dao.getProductById(1);

        assertNotNull(product, "Product should not be null");
        assertEquals(1, product.getId(), "Product ID should match");
        assertEquals("Test Product", product.getName(), "Product name should match");
        assertEquals("This is a test product", product.getDescription(), "Product description should match");
        assertEquals(9.99, product.getPrice(), 0.001, "Product price should match");
    }

    @Test
    void addProductTest() throws Exception {
        ProductDAOImpl dao = new ProductDAOImpl();
        Product product = new Product();
        product.setName("New Product");
        product.setDescription("New Description");
        product.setPrice(20.0);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.addProduct(product);

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void updateProductTest() throws Exception {
        ProductDAOImpl dao = new ProductDAOImpl();
        Product product = new Product();
        product.setId(1);
        product.setName("Updated Name");
        product.setDescription("Updated Description");
        product.setPrice(25.0);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.updateProduct(product);

        verify(mockPreparedStatement).executeUpdate();
    }


    @Test
    void deleteProductTest() throws Exception {
        ProductDAOImpl dao = new ProductDAOImpl();

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.deleteProduct(1);

        verify(mockPreparedStatement).executeUpdate();
    }
}
