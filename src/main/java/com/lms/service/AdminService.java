package com.lms.service;
import com.lms.model.*;
import com.lms.dao.*;
import java.sql.SQLException;

public class AdminService {
    private final UserDAO userDAO;
    private final CourseDAO courseDAO;

    public AdminService(UserDAO userDAO, CourseDAO courseDAO) {
        this.userDAO = userDAO;
        this.courseDAO = courseDAO;
    }

    public User createUser(String name, String email, String password, User.UserRole role) throws SQLException {
        User user = new User(0, name, email, password, role);
        userDAO.create(user);
        return user;
    }

    public Course createCourse(String title, String description, String syllabus, User instructor) throws SQLException {
        if (instructor.getRole() != User.UserRole.INSTRUCTOR) {
            throw new IllegalArgumentException("Only instructors can be assigned to courses");
        }
        Course course = new Course(0, title, description, syllabus, instructor);
        courseDAO.create(course);
        return course;
    }

    // Additional methods for analytics and system settings
} 