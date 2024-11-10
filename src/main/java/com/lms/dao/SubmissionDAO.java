package com.lms.dao;

import com.lms.model.Submission;
import com.lms.util.DatabaseConnection;
import java.sql.*;

public class SubmissionDAO {
    public void create(Submission submission, int assignmentId, int studentId) throws SQLException {
        String sql = "INSERT INTO submissions (assignment_id, student_id, content) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, assignmentId);
            stmt.setInt(2, studentId);
            stmt.setString(3, submission.getContent());
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                submission.setSubmissionId(rs.getInt(1));
            }
        }
    }

    public void updateGrade(int submissionId, double grade, String feedback) throws SQLException {
        String sql = "UPDATE submissions SET grade = ?, feedback = ? WHERE submission_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, grade);
            stmt.setString(2, feedback);
            stmt.setInt(3, submissionId);
            
            stmt.executeUpdate();
        }
    }
} 