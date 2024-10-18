package main.java.main;

import main.java.dao.OrderProcessor;
import main.java.entity.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderProcessor orderProcessor = new OrderProcessor();

        System.out.println("Welcome to the Order Management System!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create User");
            System.out.println("2. Create Product");
            System.out.println("3. Create Order");
            System.out.println("4. Cancel Order");
            System.out.println("5. Get All Products");
            System.out.println("6. Get Orders by User");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Create a new user
                    System.out.print("Enter User ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Role (Admin/User): ");
                    String role = scanner.nextLine();

                    User user = new User(userId, username, password, role);
                    orderProcessor.createUser(user);
                    break;

                case 2:
                    // Create a new product
                    System.out.print("Enter User ID (Admin only): ");
                    int adminId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter Username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String adminPassword = scanner.nextLine();
                    User adminUser = new User(adminId, adminUsername, adminPassword, "Admin");

                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter Product Name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Quantity in Stock: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter Product Type (Electronics/Clothing): ");
                    String type = scanner.nextLine();

                    if (type.equalsIgnoreCase("Electronics")) {
                        System.out.print("Enter Brand: ");
                        String brand = scanner.nextLine();
                        System.out.print("Enter Warranty Period (months): ");
                        int warrantyPeriod = scanner.nextInt();
                        Electronics electronics = new Electronics(productId, productName, description, price, quantity, brand, warrantyPeriod);
                        orderProcessor.createProduct(adminUser, electronics);
                    } else if (type.equalsIgnoreCase("Clothing")) {
                        System.out.print("Enter Size: ");
                        String size = scanner.nextLine();
                        System.out.print("Enter Color: ");
                        String color = scanner.nextLine();
                        Clothing clothing = new Clothing(productId, productName, description, price, quantity, size, color);
                        orderProcessor.createProduct(adminUser, clothing);
                    }
                    break;

                case 3:
                    // Create an order
                    System.out.print("Enter User ID: ");
                    int orderUserId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter Username: ");
                    String orderUsername = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String orderPassword = scanner.nextLine();
                    User orderUser = new User(orderUserId, orderUsername, orderPassword, "User");

                    List<Product> productList = new ArrayList<>();
                    System.out.print("How many products in the order? ");
                    int productCount = scanner.nextInt();

                    for (int i = 0; i < productCount; i++) {
                        System.out.print("Enter Product ID: ");
                        int orderProductId = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
                        Product product = orderProcessor.getAllProducts().stream()
                                .filter(p -> p.getProductId() == orderProductId)
                                .findFirst()
                                .orElse(null);
                        if (product != null) {
                            productList.add(product);
                        } else {
                            System.out.println("Product with ID " + orderProductId + " not found.");
                        }
                    }
                    orderProcessor.createOrder(orderUser, productList);
                    break;

                case 4:
                    // Cancel an order
                    System.out.print("Enter User ID: ");
                    int cancelUserId = scanner.nextInt();
                    System.out.print("Enter Order ID to cancel: ");
                    int cancelOrderId = scanner.nextInt();
                    orderProcessor.cancelOrder(cancelUserId, cancelOrderId);
                    break;

                case 5:
                    // Get all products
                    List<Product> products = orderProcessor.getAllProducts();
                    System.out.println("Products:");
                    for (Product p : products) {
                        System.out.println(p.getProductId() + ": " + p.getProductName() + " - " + p.getDescription());
                    }
                    break;

                case 6:
                    // Get orders by user
                    System.out.print("Enter User ID: ");
                    int getOrderUserId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter Username: ");
                    String getOrderUsername = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String getOrderPassword = scanner.nextLine();
                    User getOrderUser = new User(getOrderUserId, getOrderUsername, getOrderPassword, "User");

                    List<Product> orders = orderProcessor.getOrderByUser(getOrderUser);
                    System.out.println("Orders for user " + getOrderUsername + ":");
                    for (Product p : orders) {
                        System.out.println(p.getProductId() + ": " + p.getProductName() + " - " + p.getDescription());
                    }
                    break;

                case 7:
                    System.out.println("Exiting Order Management System. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}

