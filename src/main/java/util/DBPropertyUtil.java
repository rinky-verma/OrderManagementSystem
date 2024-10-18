package main.java.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static String getConnectionString(String fileName) {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream(fileName)) {
            prop.load(input);
            return prop.getProperty("db.connectionString");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

