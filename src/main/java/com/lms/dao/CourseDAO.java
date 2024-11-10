package com.lms.dao;

import com.lms.model.Course;
import com.lms.model.User;
import com.lms.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private UserDAO userDAO = new UserDAO();

    public void create(Course course) throws SQLException {
        String sql = "INSERT INTO courses (title, description, syllabus, instructor_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, course.getTitle());
            stmt.setString(2, course.getDescription());
            stmt.setString(3, course.getSyllabus());
            stmt.setInt(4, course.getInstructor().getUserId());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                course.setCourseId(rs.getInt(1));
            }
        }
    }

    public void enrollStudent(int courseId, int studentId) throws SQLException {
        String sql = "INSERT INTO course_enrollments (course_id, student_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        }
    }

    public Course findById(int courseId) throws SQLException {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCourse(rs);
            }
        }
        return null;
    }

    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        User instructor = userDAO.findById(rs.getInt("instructor_id"));
        return new Course(
            rs.getInt("course_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("syllabus"),
            instructor
        );
    }
} 