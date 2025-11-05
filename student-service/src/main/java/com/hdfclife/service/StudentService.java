package com.hdfclife.service;

import com.hdfclife.dto.StudentRequestDTO;
import com.hdfclife.dto.StudentResponseDTO;
import com.hdfclife.exception.StudentNotFoundException;
import com.hdfclife.repository.StudentRepository;
import com.hdfclife.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentResponseDTO addStudent(StudentRequestDTO dto) {
        Student student = Student.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .course(dto.getCourse())
                .build();
        Student saved = studentRepository.save(student);
        return toResponseDTO(saved);
    }

    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        return toResponseDTO(student);
    }

    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private StudentResponseDTO toResponseDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        dto.setCourse(student.getCourse());
        return dto;
    }
}

