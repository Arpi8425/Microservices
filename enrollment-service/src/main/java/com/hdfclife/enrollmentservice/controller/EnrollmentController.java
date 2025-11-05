package com.hdfclife.enrollmentservice.controller;

import com.hdfclife.enrollmentservice.dto.EnrollmentResponseDTO;
import com.hdfclife.enrollmentservice.dto.ErrorDTO;
import com.hdfclife.enrollmentservice.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll/{studentId}/{courseId}")
    public ResponseEntity<?> enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        try {
            enrollmentService.enrollStudent(studentId, courseId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage()));
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(msg));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(msg));
        }
    }

    @GetMapping("/enrollments/{studentId}")
    public ResponseEntity<?> getEnrollments(@PathVariable Long studentId) {
        try {
            EnrollmentResponseDTO response = enrollmentService.getEnrollmentsForStudent(studentId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(msg));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(msg));
        }
    }
}

