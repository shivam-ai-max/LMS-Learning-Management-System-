package com.lms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("useSSL", "false");
        properties.setProperty("allowPublicKeyRetrieval", "true");
        properties.setProperty("serverTimezone", "UTC");
        properties.setProperty("connectTimeout", "30000");
        properties.setProperty("socketTimeout", "60000");
        properties.setProperty("autoReconnect", "true");
        properties.setProperty("maxReconnects", "3");
        
        try {
            // First connect to MySQL server
            Connection conn = DriverManager.getConnection(URL, properties);
            
            // Create and select the database
            conn.createStatement().execute("CREATE DATABASE IF NOT EXISTS lms_db");
            conn.createStatement().execute("USE lms_db");
            
            conn.setAutoCommit(true);
            return conn;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database. Please check if MySQL server is running.");
            System.err.println("Error message: " + e.getMessage());
            throw e;
        }
    }
} 