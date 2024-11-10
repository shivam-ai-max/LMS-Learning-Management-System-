package com.lms.service;
import com.lms.model.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private User student;
    private List<Course> enrolledCourses;

    public StudentService(User student) {
        if (student.getRole() != User.UserRole.STUDENT) {
            throw new IllegalArgumentException("User must be a student");
        }
        this.student = student;
        this.enrolledCourses = new ArrayList<>();
    }

    public void enrollInCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            course.getEnrolledStudents().add(student);
        }
    }

    public void submitAssignment(Assignment assignment, String content) {
        if (!isEnrolledInCourseWithAssignment(assignment)) {
            throw new IllegalArgumentException("Student not enrolled in course");
        }
        Submission submission = new Submission(
            assignment.getSubmissions().size() + 1,
            student,
            assignment,
            content
        );
        assignment.getSubmissions().put(student, submission);
    }

    private boolean isEnrolledInCourseWithAssignment(Assignment assignment) {
        // Implementation to check if student is enrolled in the course containing the assignment
        return true; // Simplified for this example
    }

    // Additional student functionalities
    // ... (implement other student methods)
} 