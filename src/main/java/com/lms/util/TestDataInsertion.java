package com.lms.util;

import com.lms.model.*;
import com.lms.dao.*;
import java.sql.SQLException;
import java.util.Date;

public class TestDataInsertion {
    public static void main(String[] args) {
        try {
            // First run DatabaseTest to ensure tables are created
            DatabaseTest.main(args);
            
            // Initialize DAOs
            UserDAO userDAO = new UserDAO();
            CourseDAO courseDAO = new CourseDAO();
            AssignmentDAO assignmentDAO = new AssignmentDAO();
            SubmissionDAO submissionDAO = new SubmissionDAO();

            System.out.println("Starting test data insertion...");

            // Create Users
            System.out.println("\nCreating users...");
            User admin = new User(0, "John Admin", "admin@lms.com", "admin123", User.UserRole.ADMIN);
            User instructor1 = new User(0, "Jane Smith", "jane.smith@lms.com", "pass123", User.UserRole.INSTRUCTOR);
            User instructor2 = new User(0, "Mike Johnson", "mike.j@lms.com", "pass123", User.UserRole.INSTRUCTOR);
            User student1 = new User(0, "Alice Brown", "alice@lms.com", "pass123", User.UserRole.STUDENT);
            User student2 = new User(0, "Bob Wilson", "bob@lms.com", "pass123", User.UserRole.STUDENT);
            User student3 = new User(0, "Charlie Davis", "charlie@lms.com", "pass123", User.UserRole.STUDENT);

            userDAO.create(admin);
            userDAO.create(instructor1);
            userDAO.create(instructor2);
            userDAO.create(student1);
            userDAO.create(student2);
            userDAO.create(student3);

            System.out.println("Users created successfully!");

            // Create Courses
            System.out.println("\nCreating courses...");
            Course javaCourse = new Course(0, "Java Programming", "Learn Java fundamentals", "Week 1: Basics\nWeek 2: OOP\nWeek 3: Collections", instructor1);
            Course pythonCourse = new Course(0, "Python for Beginners", "Introduction to Python", "Week 1: Basics\nWeek 2: Functions\nWeek 3: OOP", instructor1);
            Course webDev = new Course(0, "Web Development", "HTML, CSS, JavaScript", "Week 1: HTML\nWeek 2: CSS\nWeek 3: JavaScript", instructor2);

            courseDAO.create(javaCourse);
            courseDAO.create(pythonCourse);
            courseDAO.create(webDev);

            System.out.println("Courses created successfully!");

            // Create Assignments
            System.out.println("\nCreating assignments...");
            Assignment javaAssignment1 = new Assignment(0, "Hello World", "Create a Hello World program", new Date());
            Assignment javaAssignment2 = new Assignment(0, "Calculator", "Create a simple calculator", new Date());
            Assignment pythonAssignment = new Assignment(0, "Data Analysis", "Analyze given dataset", new Date());

            assignmentDAO.create(javaAssignment1, javaCourse.getCourseId());
            assignmentDAO.create(javaAssignment2, javaCourse.getCourseId());
            assignmentDAO.create(pythonAssignment, pythonCourse.getCourseId());

            System.out.println("Assignments created successfully!");

            // Enroll Students
            System.out.println("\nEnrolling students...");
            courseDAO.enrollStudent(javaCourse.getCourseId(), student1.getUserId());
            courseDAO.enrollStudent(javaCourse.getCourseId(), student2.getUserId());
            courseDAO.enrollStudent(pythonCourse.getCourseId(), student1.getUserId());
            courseDAO.enrollStudent(pythonCourse.getCourseId(), student3.getUserId());
            courseDAO.enrollStudent(webDev.getCourseId(), student2.getUserId());
            courseDAO.enrollStudent(webDev.getCourseId(), student3.getUserId());

            System.out.println("Students enrolled successfully!");

            // Submit Assignments
            System.out.println("\nSubmitting assignments...");
            Submission submission1 = new Submission(0, student1, javaAssignment1, "public class HelloWorld { public static void main(String[] args) { System.out.println(\"Hello World!\"); } }");
            Submission submission2 = new Submission(0, student2, javaAssignment1, "public class Main { public static void main(String[] args) { System.out.println(\"Hello Java!\"); } }");

            submissionDAO.create(submission1, javaAssignment1.getAssignmentId(), student1.getUserId());
            submissionDAO.create(submission2, javaAssignment1.getAssignmentId(), student2.getUserId());

            // Grade Submissions
            submissionDAO.updateGrade(submission1.getSubmissionId(), 95.0, "Excellent work!");
            submissionDAO.updateGrade(submission2.getSubmissionId(), 88.0, "Good effort, but could be improved");

            System.out.println("Assignments submitted and graded successfully!");

            // Print Summary
            System.out.println("\nTest Data Summary:");
            System.out.println("Users created: " + 6);
            System.out.println("Courses created: " + 3);
            System.out.println("Assignments created: " + 3);
            System.out.println("Students enrolled: " + 6);
            System.out.println("Submissions made: " + 2);

            System.out.println("\nTest data insertion completed successfully!");

        } catch (SQLException e) {
            System.err.println("Error occurred while inserting test data: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 