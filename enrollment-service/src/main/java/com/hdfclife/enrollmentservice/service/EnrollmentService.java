package com.hdfclife.enrollmentservice.service;

import com.hdfclife.enrollmentservice.dto.CourseDTO;
import com.hdfclife.enrollmentservice.dto.EnrollmentResponseDTO;
import com.hdfclife.enrollmentservice.dto.StudentDTO;
import com.hdfclife.enrollmentservice.entity.Enrollment;
import com.hdfclife.enrollmentservice.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final RestTemplate restTemplate;

    @Value("${student.service.url}")
    private String studentServiceUrl;
    @Value("${course.service.url}")
    private String courseServiceUrl;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, RestTemplate restTemplate) {
        this.enrollmentRepository = enrollmentRepository;
        this.restTemplate = restTemplate;
    }

    public void enrollStudent(Long studentId, Long courseId) {
        // Check if student exists
        ResponseEntity<StudentDTO> studentResponse = restTemplate.getForEntity(studentServiceUrl + "/students/" + studentId, StudentDTO.class);
        if (!studentResponse.getStatusCode().is2xxSuccessful() || studentResponse.getBody() == null) {
            throw new RuntimeException("Student not found");
        }
        // Check if course exists
        ResponseEntity<CourseDTO> courseResponse = restTemplate.getForEntity(courseServiceUrl + "/courses/" + courseId, CourseDTO.class);
        if (!courseResponse.getStatusCode().is2xxSuccessful() || courseResponse.getBody() == null) {
            throw new RuntimeException("Course not found");
        }
        // Check if already enrolled
        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalArgumentException("Student already enrolled in this course");
        }
        Enrollment enrollment = Enrollment.of(studentId, courseId);
        enrollmentRepository.save(enrollment);
    }

    public EnrollmentResponseDTO getEnrollmentsForStudent(Long studentId) {
        // Get student
        ResponseEntity<StudentDTO> studentResponse = restTemplate.getForEntity(studentServiceUrl + "/students/" + studentId, StudentDTO.class);
        if (!studentResponse.getStatusCode().is2xxSuccessful() || studentResponse.getBody() == null) {
            throw new RuntimeException("Student not found");
        }
        StudentDTO student = studentResponse.getBody();
        // Get enrollments
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        // Get course names
        List<String> courseNames = enrollments.stream().map(e -> {
            ResponseEntity<CourseDTO> courseResponse = restTemplate.getForEntity(courseServiceUrl + "/courses/" + e.getCourseId(), CourseDTO.class);
            if (courseResponse.getStatusCode().is2xxSuccessful() && courseResponse.getBody() != null) {
                return courseResponse.getBody().getCourseName();
            } else {
                return "Unknown";
            }
        }).collect(Collectors.toList());
        return new EnrollmentResponseDTO(student.getName(), courseNames);
    }
}
