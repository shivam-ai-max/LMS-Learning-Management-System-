package com.lms;
import com.lms.model.*;
import com.lms.service.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Initialize services
        AdminService adminService = new AdminService();
        InstructorService instructorService = new InstructorService();

        // Create users
        adminService.createUser("John Admin", "admin@lms.com", "password123", User.UserRole.ADMIN);
        adminService.createUser("Jane Instructor", "instructor@lms.com", "password123", User.UserRole.INSTRUCTOR);
        adminService.createUser("Bob Student", "student@lms.com", "password123", User.UserRole.STUDENT);

        // Create a course
        User instructor = new User(2, "Jane Instructor", "instructor@lms.com", "password123", User.UserRole.INSTRUCTOR);
        Course javaCourse = new Course(1, "Java Programming", "Learn Java basics", "Week 1: Introduction...", instructor);
        adminService.createCourse("Java Programming", "Learn Java basics", "Week 1: Introduction...", instructor);
        
        // Add course to instructor's courses
        instructorService.addCourse(javaCourse);
        
        // Create an assignment
        instructorService.createAssignment(javaCourse, "First Assignment", "Create a Hello World program", new Date());

        // Initialize student service
        User student = new User(3, "Bob Student", "student@lms.com", "password123", User.UserRole.STUDENT);
        StudentService studentService = new StudentService(student);

        // Enroll student in course
        studentService.enrollInCourse(javaCourse);

        // Submit assignment
        Assignment assignment = javaCourse.getAssignments().get(0);
        studentService.submitAssignment(assignment, "public class HelloWorld { ... }");

        // Grade submission
        Submission submission = assignment.getSubmissions().get(student);
        instructorService.gradeSubmission(submission, 95.0, "Great work!");

        System.out.println("LMS Demo completed successfully!");
        
        // Add some print statements to show the system is working
        System.out.println("\nCourse Details:");
        System.out.println("Title: " + javaCourse.getTitle());
        System.out.println("Number of enrolled students: " + javaCourse.getEnrolledStudents().size());
        System.out.println("Number  of assignments: " + javaCourse.getAssignments().size());
    }
} 