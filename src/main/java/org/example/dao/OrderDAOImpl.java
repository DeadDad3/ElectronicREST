package org.example.dao;

import org.example.entity.Order;
import org.example.entity.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private ProductDAO productDAO;

    public OrderDAOImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO orders DEFAULT VALUES RETURNING id;";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order getOrderById(int id) {
        Order order = new Order();
        String sql = "SELECT * FROM order_products WHERE orderId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                Product product = getProductById(productId); // Assuming getProductById is implemented in ProductDAOImpl
                products.add(product);
            }
            order.setId(id);
            order.setProducts(products);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public void deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addProductToOrder(int orderId, int productId) {
        String sql = "INSERT INTO order_products (orderId, productId) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeProductFromOrder(int orderId, int productId) {
        String sql = "DELETE FROM order_products WHERE orderId = ? AND productId = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }

}
