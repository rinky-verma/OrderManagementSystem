package main.java.dao;

import main.java.entity.Product;
import main.java.entity.User;
import main.java.util.DBConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessor implements IOrderManagementRepository {
    private Map<Integer, List<Product>> userOrders = new HashMap<>();
    private List<Product> productList = new ArrayList<>();
    // private int orderIdCounter = 1;

    @Override
    public void createOrder(User user, List<Product> products) {
        if (user != null && products != null && !products.isEmpty()) {
            userOrders.putIfAbsent(user.getUserId(), new ArrayList<>());
            userOrders.get(user.getUserId()).addAll(products);
            System.out.println("Order created for user: " + user.getUsername());
        } else {
            throw new IllegalArgumentException("Invalid user or product list");
        }
    }

    @Override
    public void cancelOrder(int userId, int orderId) {
        if (userOrders.containsKey(userId)) {
            List<Product> orders = userOrders.get(userId);
            if (orderId >= 0 && orderId < orders.size()) {
                orders.remove(orderId);
                System.out.println("Order with ID " + orderId + " canceled for user ID " + userId);
            } else {
                throw new IllegalArgumentException("Order not found");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public void createProduct(User user, Product product) {
        if ("Admin".equals(user.getRole())) {
            productList.add(product);
            System.out.println("Product created: " + product.getProductName());
        } else {
            throw new SecurityException("Only admin can create products");
        }
    }

    @Override
    public void createUser(User user) {
        try (Connection conn = DBConnUtil.getDBConn()) {
            if (conn != null) {
                String sql = "INSERT INTO user (userId, username, password, role) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, user.getUserId());
                stmt.setString(2, user.getUsername());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getRole());
                stmt.executeUpdate();
                System.out.println("User created: " + user.getUsername());
            } else {
                System.out.println("Connection to the database failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print error for debugging
        }
    }
    

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public List<Product> getOrderByUser(User user) {
        return userOrders.getOrDefault(user.getUserId(), new ArrayList<>());
    }
}
