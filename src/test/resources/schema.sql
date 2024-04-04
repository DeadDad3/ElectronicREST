CREATE TABLE orders (
    id SERIAL PRIMARY KEY
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(255)
);

CREATE TABLE order_products (
    orderid INT,
    productid INT,
    PRIMARY KEY (orderid, productid),
    FOREIGN KEY (orderid) REFERENCES orders(id),
    FOREIGN KEY (productid) REFERENCES products(id)
);
