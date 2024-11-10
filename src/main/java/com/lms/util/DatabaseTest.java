package com.lms.util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseTest {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            System.out.println("Testing database connection...");
            
            // Test connection
            conn = DatabaseConnection.getConnection();
            System.out.println("Database connected successfully!");
            
            // Create database if it doesn't exist
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS lms_db");
            stmt.execute("USE lms_db");
            System.out.println("Database 'lms_db' selected.");
            
            // Drop existing tables
            System.out.println("Dropping existing tables...");
            String[] dropTables = {
                "DROP TABLE IF EXISTS submissions",
                "DROP TABLE IF EXISTS course_enrollments",
                "DROP TABLE IF EXISTS assignments",
                "DROP TABLE IF EXISTS courses",
                "DROP TABLE IF EXISTS users"
            };
            
            for (String dropTable : dropTables) {
                stmt.execute(dropTable);
            }
            
            // Create tables
            System.out.println("Creating tables...");
            String[] createTables = {
                // Users table
                "CREATE TABLE users (" +
                "user_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) UNIQUE NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "role ENUM('ADMIN', 'INSTRUCTOR', 'STUDENT') NOT NULL)",

                // Courses table
                "CREATE TABLE courses (" +
                "course_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "title VARCHAR(200) NOT NULL, " +
                "description TEXT, " +
                "syllabus TEXT, " +
                "instructor_id INT, " +
                "FOREIGN KEY (instructor_id) REFERENCES users(user_id))",

                // Course enrollments table
                "CREATE TABLE course_enrollments (" +
                "enrollment_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "course_id INT, " +
                "student_id INT, " +
                "enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (course_id) REFERENCES courses(course_id), " +
                "FOREIGN KEY (student_id) REFERENCES users(user_id), " +
                "UNIQUE KEY unique_enrollment (course_id, student_id))",

                // Assignments table
                "CREATE TABLE assignments (" +
                "assignment_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "course_id INT, " +
                "title VARCHAR(200) NOT NULL, " +
                "description TEXT, " +
                "due_date TIMESTAMP, " +
                "FOREIGN KEY (course_id) REFERENCES courses(course_id))",

                // Submissions table
                "CREATE TABLE submissions (" +
                "submission_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "assignment_id INT, " +
                "student_id INT, " +
                "content TEXT, " +
                "submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "grade DECIMAL(5,2), " +
                "feedback TEXT, " +
                "FOREIGN KEY (assignment_id) REFERENCES assignments(assignment_id), " +
                "FOREIGN KEY (student_id) REFERENCES users(user_id), " +
                "UNIQUE KEY unique_submission (assignment_id, student_id))"
            };

            for (String createTable : createTables) {
                stmt.execute(createTable);
            }
            
            System.out.println("Tables created successfully!");
            
            // Test query
            ResultSet rs = stmt.executeQuery("SHOW TABLES");
            System.out.println("\nExisting tables:");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            
            System.out.println("\nDatabase test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Database connection closed.");
                } catch (Exception e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
} 