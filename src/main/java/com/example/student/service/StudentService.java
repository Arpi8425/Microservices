package com.example.student.service;

import com.example.student.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO addStudent(StudentDTO dto);
    StudentDTO getStudentById(Long id);
    List<StudentDTO> getAllStudents();
}
