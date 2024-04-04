package org.example.dao;

import org.example.entity.Product;

import java.util.List;

public interface ProductDAO {
    Product getProductById(int id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryId(int categoryId);
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int id);
}