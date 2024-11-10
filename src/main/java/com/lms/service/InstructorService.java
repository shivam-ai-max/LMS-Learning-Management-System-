package com.lms.service;
import com.lms.model.*;
import com.lms.dao.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class InstructorService {
    private final CourseDAO courseDAO;
    private final AssignmentDAO assignmentDAO;
    private final SubmissionDAO submissionDAO;

    public InstructorService(CourseDAO courseDAO, AssignmentDAO assignmentDAO, SubmissionDAO submissionDAO) {
        this.courseDAO = courseDAO;
        this.assignmentDAO = assignmentDAO;
        this.submissionDAO = submissionDAO;
    }

    public void addCourse(Course course) throws SQLException {
        courseDAO.create(course);
    }

    public Assignment createAssignment(Course course, String title, String description, Date dueDate) throws SQLException {
        Assignment assignment = new Assignment(0, title, description, dueDate);
        assignmentDAO.create(assignment, course.getCourseId());
        return assignment;
    }

    public void gradeSubmission(Submission submission, double grade, String feedback) throws SQLException {
        submission.setGrade(grade);
        submission.setFeedback(feedback);
        submissionDAO.updateGrade(submission.getSubmissionId(), grade, feedback);
    }

    public List<Course> getInstructorCourses(int instructorId) throws SQLException {
        return courseDAO.findByInstructorId(instructorId);
    }

    public Course getCourseById(int courseId) throws SQLException {
        return courseDAO.findById(courseId);
    }
} 