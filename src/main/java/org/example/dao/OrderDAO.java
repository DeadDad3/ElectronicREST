package org.example.dao;

import org.example.entity.Order;

public interface OrderDAO {
    void addOrder(Order order); // Добавление нового заказа
    Order getOrderById(int id); // Получение заказа по ID
    void deleteOrder(int id); // Удаление заказа по ID
    void addProductToOrder(int orderId, int productId); // Добавление продукта к заказу
    void removeProductFromOrder(int orderId, int productId); // Удаление продукта из заказа
}
