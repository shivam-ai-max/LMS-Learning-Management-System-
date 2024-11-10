package com.lms;
import com.lms.model.*;
import com.lms.service.*;
import com.lms.dao.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();
    private static CourseDAO courseDAO = new CourseDAO();
    private static AssignmentDAO assignmentDAO = new AssignmentDAO();
    private static SubmissionDAO submissionDAO = new SubmissionDAO();
    private static AdminService adminService = new AdminService(userDAO, courseDAO);
    private static InstructorService instructorService = new InstructorService(courseDAO, assignmentDAO, submissionDAO);
    private static StudentService studentService = null;
    private static User currentUser = null;

    public static void main(String[] args) {
        try {
            while (true) {
                if (currentUser == null) {
                    showLoginMenu();
                } else {
                    switch (currentUser.getRole()) {
                        case ADMIN:
                            showAdminMenu();
                            break;
                        case INSTRUCTOR:
                            showInstructorMenu();
                            break;
                        case STUDENT:
                            showStudentMenu();
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showLoginMenu() throws SQLException {
        System.out.println("\n=== LMS Login ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            // In a real system, you'd verify the password properly
            currentUser = userDAO.findByEmail(email);
            if (currentUser != null && currentUser.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome, " + currentUser.getName());
            } else {
                System.out.println("Invalid credentials!");
                currentUser = null;
            }
        } else if (choice == 2) {
            System.out.println("Thank you for using LMS. Goodbye!");
            System.exit(0);
        }
    }

    private static void showAdminMenu() throws SQLException {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Create User");
        System.out.println("2. Create Course");
        System.out.println("3. View All Users");
        System.out.println("4. View All Courses");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                createUser();
                break;
            case 2:
                createCourse();
                break;
            case 3:
                viewAllUsers();
                break;
            case 4:
                viewAllCourses();
                break;
            case 5:
                logout();
                break;
        }
    }

    private static void showInstructorMenu() throws SQLException {
        System.out.println("\n=== Instructor Menu ===");
        System.out.println("1. View My Courses");
        System.out.println("2. Create Assignment");
        System.out.println("3. Grade Submission");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                viewInstructorCourses();
                break;
            case 2:
                createAssignment();
                break;
            case 3:
                gradeSubmission();
                break;
            case 4:
                logout();
                break;
        }
    }

    private static void showStudentMenu() throws SQLException {
        System.out.println("\n=== Student Menu ===");
        System.out.println("1. View Available Courses");
        System.out.println("2. View My Courses");
        System.out.println("3. Enroll in Course");
        System.out.println("4. View Assignments");
        System.out.println("5. Submit Assignment");
        System.out.println("6. View My Grades");
        System.out.println("7. Logout");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                viewAllCourses();
                break;
            case 2:
                viewEnrolledCourses();
                break;
            case 3:
                enrollInCourse();
                break;
            case 4:
                viewAssignments();
                break;
            case 5:
                submitAssignment();
                break;
            case 6:
                viewGrades();
                break;
            case 7:
                logout();
                break;
        }
    }

    // Admin functions
    private static void createUser() throws SQLException {
        System.out.println("\n=== Create User ===");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("Select role:");
        System.out.println("1. Admin");
        System.out.println("2. Instructor");
        System.out.println("3. Student");
        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        User.UserRole role;
        switch (roleChoice) {
            case 1:
                role = User.UserRole.ADMIN;
                break;
            case 2:
                role = User.UserRole.INSTRUCTOR;
                break;
            case 3:
                role = User.UserRole.STUDENT;
                break;
            default:
                System.out.println("Invalid role choice!");
                return;
        }

        adminService.createUser(name, email, password, role);
        System.out.println("User created successfully!");
    }

    private static void createCourse() throws SQLException {
        System.out.println("\n=== Create Course ===");
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter syllabus: ");
        String syllabus = scanner.nextLine();
        
        // List available instructors
        List<User> instructors = userDAO.findByRole(User.UserRole.INSTRUCTOR);
        System.out.println("\nAvailable Instructors:");
        for (User instructor : instructors) {
            System.out.println(instructor.getUserId() + ". " + instructor.getName());
        }
        System.out.print("Select instructor ID: ");
        int instructorId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        User instructor = userDAO.findById(instructorId);
        adminService.createCourse(title, description, syllabus, instructor);
        System.out.println("Course created successfully!");
    }

    // Helper functions
    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully!");
    }

    // Admin helper methods
    private static void viewAllUsers() throws SQLException {
        List<User> users = userDAO.findAll();
        System.out.println("\n=== All Users ===");
        for (User user : users) {
            System.out.println("ID: " + user.getUserId() + 
                             " | Name: " + user.getName() + 
                             " | Email: " + user.getEmail() + 
                             " | Role: " + user.getRole());
        }
    }

    private static void viewAllCourses() throws SQLException {
        List<Course> courses = courseDAO.findAll();
        System.out.println("\n=== All Courses ===");
        for (Course course : courses) {
            System.out.println("ID: " + course.getCourseId() + 
                             " | Title: " + course.getTitle() + 
                             " | Instructor: " + course.getInstructor().getName());
        }
    }

    // Instructor helper methods
    private static void viewInstructorCourses() throws SQLException {
        List<Course> courses = instructorService.getInstructorCourses(currentUser.getUserId());
        System.out.println("\n=== My Courses ===");
        for (Course course : courses) {
            System.out.println("ID: " + course.getCourseId() + 
                             " | Title: " + course.getTitle() + 
                             " | Students: " + courseDAO.getEnrolledStudentCount(course.getCourseId()));
        }
    }

    private static void createAssignment() throws SQLException {
        System.out.println("\n=== Create Assignment ===");
        
        // First show instructor's courses
        List<Course> courses = instructorService.getInstructorCourses(currentUser.getUserId());
        System.out.println("\nYour Courses:");
        for (Course course : courses) {
            System.out.println(course.getCourseId() + ". " + course.getTitle());
        }
        
        System.out.print("Select course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        Course course = courseDAO.findById(courseId);
        if (course == null || course.getInstructor().getUserId() != currentUser.getUserId()) {
            System.out.println("Invalid course selection!");
            return;
        }
        
        System.out.print("Enter assignment title: ");
        String title = scanner.nextLine();
        System.out.print("Enter assignment description: ");
        String description = scanner.nextLine();
        
        // For simplicity, using current date + 7 days as due date
        Date dueDate = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
        
        instructorService.createAssignment(course, title, description, dueDate);
        System.out.println("Assignment created successfully!");
    }

    private static void gradeSubmission() throws SQLException {
        System.out.println("\n=== Grade Submission ===");
        
        // Show instructor's courses
        List<Course> courses = instructorService.getInstructorCourses(currentUser.getUserId());
        System.out.println("\nYour Courses:");
        for (Course course : courses) {
            System.out.println(course.getCourseId() + ". " + course.getTitle());
        }
        
        System.out.print("Select course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Show assignments for selected course
        List<Assignment> assignments = assignmentDAO.findByCourseId(courseId);
        System.out.println("\nAssignments:");
        for (Assignment assignment : assignments) {
            System.out.println(assignment.getAssignmentId() + ". " + assignment.getTitle());
        }
        
        System.out.print("Select assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Show submissions for selected assignment
        List<Submission> submissions = submissionDAO.findByAssignmentId(assignmentId);
        System.out.println("\nSubmissions:");
        for (Submission submission : submissions) {
            System.out.println(submission.getSubmissionId() + ". Student: " + submission.getStudent().getName());
        }
        
        System.out.print("Select submission ID: ");
        int submissionId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter grade (0-100): ");
        double grade = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter feedback: ");
        String feedback = scanner.nextLine();
        
        Submission submission = submissionDAO.findById(submissionId);
        instructorService.gradeSubmission(submission, grade, feedback);
        System.out.println("Submission graded successfully!");
    }

    // Student helper methods
    private static void viewEnrolledCourses() throws SQLException {
        if (studentService == null) {
            studentService = new StudentService(currentUser, courseDAO, assignmentDAO, submissionDAO);
        }
        List<Course> courses = studentService.getEnrolledCourses();
        System.out.println("\n=== My Courses ===");
        for (Course course : courses) {
            System.out.println("ID: " + course.getCourseId() + 
                             " | Title: " + course.getTitle() + 
                             " | Instructor: " + course.getInstructor().getName());
        }
    }

    private static void enrollInCourse() throws SQLException {
        if (studentService == null) {
            studentService = new StudentService(currentUser, courseDAO, assignmentDAO, submissionDAO);
        }
        
        System.out.println("\n=== Enroll in Course ===");
        List<Course> availableCourses = courseDAO.findAll();
        System.out.println("\nAvailable Courses:");
        for (Course course : availableCourses) {
            System.out.println(course.getCourseId() + ". " + course.getTitle() + 
                             " (Instructor: " + course.getInstructor().getName() + ")");
        }
        
        System.out.print("Select course ID to enroll: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        Course course = courseDAO.findById(courseId);
        if (course != null) {
            studentService.enrollInCourse(course);
            System.out.println("Enrolled successfully!");
        } else {
            System.out.println("Invalid course selection!");
        }
    }

    private static void viewAssignments() throws SQLException {
        if (studentService == null) {
            studentService = new StudentService(currentUser, courseDAO, assignmentDAO, submissionDAO);
        }
        
        List<Course> enrolledCourses = studentService.getEnrolledCourses();
        System.out.println("\n=== Assignments ===");
        for (Course course : enrolledCourses) {
            System.out.println("\nCourse: " + course.getTitle());
            List<Assignment> assignments = assignmentDAO.findByCourseId(course.getCourseId());
            for (Assignment assignment : assignments) {
                System.out.println("ID: " + assignment.getAssignmentId() + 
                                 " | Title: " + assignment.getTitle() + 
                                 " | Due: " + assignment.getDueDate());
            }
        }
    }

    private static void submitAssignment() throws SQLException {
        if (studentService == null) {
            studentService = new StudentService(currentUser, courseDAO, assignmentDAO, submissionDAO);
        }
        
        System.out.println("\n=== Submit Assignment ===");
        viewAssignments();
        
        System.out.print("\nEnter assignment ID: ");
        int assignmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter your submission content: ");
        String content = scanner.nextLine();
        
        Assignment assignment = assignmentDAO.findById(assignmentId);
        if (assignment != null) {
            studentService.submitAssignment(assignment, content);
            System.out.println("Assignment submitted successfully!");
        } else {
            System.out.println("Invalid assignment selection!");
        }
    }

    private static void viewGrades() throws SQLException {
        if (studentService == null) {
            studentService = new StudentService(currentUser, courseDAO, assignmentDAO, submissionDAO);
        }
        
        System.out.println("\n=== My Grades ===");
        List<Submission> submissions = submissionDAO.findByStudentId(currentUser.getUserId());
        for (Submission submission : submissions) {
            System.out.println("Assignment: " + submission.getAssignment().getTitle() + 
                             " | Grade: " + (submission.getGrade() != null ? submission.getGrade() : "Not graded") + 
                             " | Feedback: " + (submission.getFeedback() != null ? submission.getFeedback() : "No feedback yet"));
        }
    }
} 