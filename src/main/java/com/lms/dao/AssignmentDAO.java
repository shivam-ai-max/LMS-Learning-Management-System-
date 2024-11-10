package com.lms.dao;

import com.lms.model.Assignment;
import com.lms.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {
    public void create(Assignment assignment, int courseId) throws SQLException {
        String sql = "INSERT INTO assignments (course_id, title, description, due_date) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, courseId);
            stmt.setString(2, assignment.getTitle());
            stmt.setString(3, assignment.getDescription());
            stmt.setTimestamp(4, new Timestamp(assignment.getDueDate().getTime()));
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                assignment.setAssignmentId(rs.getInt(1));
            }
        }
    }

    public Assignment findById(int assignmentId) throws SQLException {
        String sql = "SELECT * FROM assignments WHERE assignment_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, assignmentId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToAssignment(rs);
            }
        }
        return null;
    }

    public List<Assignment> findByCourseId(int courseId) throws SQLException {
        String sql = "SELECT * FROM assignments WHERE course_id = ?";
        List<Assignment> assignments = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                assignments.add(mapResultSetToAssignment(rs));
            }
        }
        return assignments;
    }

    private Assignment mapResultSetToAssignment(ResultSet rs) throws SQLException {
        return new Assignment(
            rs.getInt("assignment_id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getTimestamp("due_date")
        );
    }
} 