package com.lms.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int courseId;
    private String title;
    private String description;
    private String syllabus;
    private User instructor;
    private List<User> enrolledStudents;
    private List<Assignment> assignments;

    public Course(int courseId, String title, String description, String syllabus, User instructor) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.syllabus = syllabus;
        this.instructor = instructor;
        this.enrolledStudents = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public List<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public User getInstructor() {
        return instructor;
    }
} 