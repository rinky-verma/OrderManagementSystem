package main.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class DBConnUtil {
    public static Connection getDBConn() {
        Connection conn = null;
        try {
            Properties props = new Properties();
            InputStream input = new FileInputStream(
                    "C:/Users/LENOVO/Desktop/order_management_system/OrderManagementSystem/src/main/resources/db.properties");

            // InputStream input =
            // DBConnUtil.class.getClassLoader().getResourceAsStream("db.properties");
            // if (input == null) {
            // System.out.println("Sorry, unable to find db.properties");
            // return null;
            // }
            props.load(input);
            String connectionString = props.getProperty("db.connectionString");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");
            conn = DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
