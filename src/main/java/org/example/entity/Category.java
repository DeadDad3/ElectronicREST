package org.example.entity;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}