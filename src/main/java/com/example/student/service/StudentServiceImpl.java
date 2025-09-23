package com.example.student.service;

import com.example.student.dto.StudentDTO;
import com.example.student.entity.Student;
import com.example.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    private StudentDTO toDto(Student s) {
        return new StudentDTO(s.getId(), s.getName(), s.getAge(), s.getCourse());
    }

    @Override
    public StudentDTO addStudent(StudentDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Name required");
        }
        Student s = new Student(dto.getName(), dto.getAge(), dto.getCourse());
        Student saved = repo.save(s);
        return toDto(saved);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student s = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Student not found"));
        return toDto(s);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }
}
