package com.lms.dao;

import com.lms.model.Assignment;
import com.lms.model.Submission;
import com.lms.model.User;
import com.lms.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubmissionDAO {
    private final UserDAO userDAO;
    private final AssignmentDAO assignmentDAO;

    public SubmissionDAO() {
        this.userDAO = new UserDAO();
        this.assignmentDAO = new AssignmentDAO();
    }

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

    public List<Submission> findByAssignmentId(int assignmentId) throws SQLException {
        String sql = "SELECT * FROM submissions WHERE assignment_id = ?";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, assignmentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        }
        return submissions;
    }

    public Submission findById(int submissionId) throws SQLException {
        String sql = "SELECT * FROM submissions WHERE submission_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, submissionId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToSubmission(rs);
            }
        }
        return null;
    }

    public List<Submission> findByStudentId(int studentId) throws SQLException {
        String sql = "SELECT * FROM submissions WHERE student_id = ?";
        List<Submission> submissions = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                submissions.add(mapResultSetToSubmission(rs));
            }
        }
        return submissions;
    }

    private Submission mapResultSetToSubmission(ResultSet rs) throws SQLException {
        User student = userDAO.findById(rs.getInt("student_id"));
        Assignment assignment = assignmentDAO.findById(rs.getInt("assignment_id"));
        
        Submission submission = new Submission(
            rs.getInt("submission_id"),
            student,
            assignment,
            rs.getString("content")
        );
        
        // Set additional fields if they exist
        Double grade = rs.getDouble("grade");
        if (!rs.wasNull()) {
            submission.setGrade(grade);
        }
        
        String feedback = rs.getString("feedback");
        if (feedback != null) {
            submission.setFeedback(feedback);
        }
        
        return submission;
    }
} 