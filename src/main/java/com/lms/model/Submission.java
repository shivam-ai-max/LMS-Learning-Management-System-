package com.lms.model;

import java.util.Date;

public class Submission {
    private int submissionId;
    private User student;
    private Assignment assignment;
    private String content;
    private Date submissionDate;
    private Double grade;
    private String feedback;

    public Submission(int submissionId, User student, Assignment assignment, String content) {
        this.submissionId = submissionId;
        this.student = student;
        this.assignment = assignment;
        this.content = content;
        this.submissionDate = new Date();
    }

    // Getters and Setters
    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
} 