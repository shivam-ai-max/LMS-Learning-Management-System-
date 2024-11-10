package com.lms.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Assignment {
    private int assignmentId;
    private String title;
    private String description;
    private Date dueDate;
    private Map<User, Submission> submissions;

    public Assignment(int assignmentId, String title, String description, Date dueDate) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.submissions = new HashMap<>();
    }

    // Getters and Setters
    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Map<User, Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Map<User, Submission> submissions) {
        this.submissions = submissions;
    }
} 