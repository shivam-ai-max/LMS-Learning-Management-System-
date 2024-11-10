package com.lms.service;
import com.lms.model.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstructorService {
    private List<Course> instructorCourses;

    public InstructorService() {
        this.instructorCourses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        if (!instructorCourses.contains(course)) {
            instructorCourses.add(course);
        }
    }

    public void createAssignment(Course course, String title, String description, Date dueDate) {
        if (!instructorCourses.contains(course)) {
            throw new IllegalArgumentException("Instructor not authorized for this course");
        }
        int assignmentId = course.getAssignments().size() + 1;
        Assignment assignment = new Assignment(assignmentId, title, description, dueDate);
        course.getAssignments().add(assignment);
    }

    public void gradeSubmission(Submission submission, double grade, String feedback) {
        submission.setGrade(grade);
        submission.setFeedback(feedback);
    }

    // Additional instructor functionalities
    // ... (implement other instructor methods)
} 