package com.lms.service;
import com.lms.model.*;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    private List<User> users;
    private List<Course> courses;

    public AdminService() {
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public void createUser(String name, String email, String password, User.UserRole role) {
        int userId = users.size() + 1; // Simple ID generation
        User user = new User(userId, name, email, password, role);
        users.add(user);
    }

    public void createCourse(String title, String description, String syllabus, User instructor) {
        if (instructor.getRole() != User.UserRole.INSTRUCTOR) {
            throw new IllegalArgumentException("Only instructors can be assigned to courses");
        }
        int courseId = courses.size() + 1;
        Course course = new Course(courseId, title, description, syllabus, instructor);
        courses.add(course);
    }

    // Additional admin functionalities
    // ... (implement other admin methods)
} 