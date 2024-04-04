-- Добавление продуктов
INSERT INTO products (name, description, price, category) VALUES ('Gaming Laptop', 'A laptop with high-end specifications for gaming', 1200.00, 'Electronics');
INSERT INTO products (name, description, price, category) VALUES ('Wireless Mouse', 'A high-precision wireless mouse', 50.00, 'Accessories');

-- Добавление заказа
INSERT INTO orders DEFAULT VALUES; -- Создает заказ с ID 1

-- Связывание продуктов с заказом
INSERT INTO order_products (orderid, productid) VALUES (1, 1);
INSERT INTO order_products (orderid, productid) VALUES (1, 2);
