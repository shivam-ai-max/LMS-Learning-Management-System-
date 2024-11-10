package com.lms.service;
import com.lms.model.*;
import com.lms.dao.*;
import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final User student;
    private final CourseDAO courseDAO;
    private final AssignmentDAO assignmentDAO;
    private final SubmissionDAO submissionDAO;

    public StudentService(User student, CourseDAO courseDAO, AssignmentDAO assignmentDAO, SubmissionDAO submissionDAO) {
        if (student.getRole() != User.UserRole.STUDENT) {
            throw new IllegalArgumentException("User must be a student");
        }
        this.student = student;
        this.courseDAO = courseDAO;
        this.assignmentDAO = assignmentDAO;
        this.submissionDAO = submissionDAO;
    }

    public void enrollInCourse(Course course) throws SQLException {
        courseDAO.enrollStudent(course.getCourseId(), student.getUserId());
    }

    public void submitAssignment(Assignment assignment, String content) throws SQLException {
        Submission submission = new Submission(0, student, assignment, content);
        submissionDAO.create(submission, assignment.getAssignmentId(), student.getUserId());
    }

    public List<Course> getEnrolledCourses() throws SQLException {
        return courseDAO.findByStudentId(student.getUserId());
    }
} 