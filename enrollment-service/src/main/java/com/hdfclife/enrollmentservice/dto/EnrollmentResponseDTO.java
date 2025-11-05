package com.hdfclife.enrollmentservice.dto;

import java.util.List;

public class EnrollmentResponseDTO {
    private String student;
    private List<String> enrolledCourses;
    public EnrollmentResponseDTO() {}
    public EnrollmentResponseDTO(String student, List<String> enrolledCourses) {
        this.student = student;
        this.enrolledCourses = enrolledCourses;
    }
    public String getStudent() { return student; }
    public void setStudent(String student) { this.student = student; }
    public List<String> getEnrolledCourses() { return enrolledCourses; }
    public void setEnrolledCourses(List<String> enrolledCourses) { this.enrolledCourses = enrolledCourses; }
}
