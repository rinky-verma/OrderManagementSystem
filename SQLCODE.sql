create database OrderManagementSystem;
use OrderManagementSystem;
CREATE TABLE User (
    userId INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(10) NOT NULL CHECK (role IN ('Admin', 'User'))
);
CREATE TABLE Product (
    productId INT PRIMARY KEY,
    productName VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    quantityInStock INT NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('Electronics', 'Clothing'))
);
CREATE TABLE OrderTable (
    orderId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT,
    orderDate DATE,
    FOREIGN KEY (userId) REFERENCES User(userId)
);
CREATE TABLE OrderDetails (
    orderId INT,
    productId INT,
    quantity INT NOT NULL,
    FOREIGN KEY (orderId) REFERENCES OrderTable(orderId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);
INSERT INTO User (userId, username, password, role) 
VALUES (1, 'john_doe', 'password123', 'User');


INSERT INTO Product (productId, productName, description, price, quantityInStock, type) 
VALUES (1, 'Laptop', 'Gaming Laptop with 16GB RAM', 1200.00, 10, 'Electronics');
INSERT INTO OrderTable (userId, orderDate)
VALUES (1, CURDATE());
INSERT INTO OrderDetails (orderId, productId, quantity) 
VALUES (1, 1, 2);


