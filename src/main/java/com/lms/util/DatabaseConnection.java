package com.lms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database URL format: jdbc:mysql://hostname:port/database_name
    private static final String URL = "jdbc:mysql://localhost:3306/lms_db";
    
    // For XAMPP default credentials
    private static final String USERNAME = "root";  // default XAMPP MySQL username
    private static final String PASSWORD = "";      // default XAMPP MySQL password is empty
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
} 