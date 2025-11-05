package com.hdfclife.course.service;

import com.hdfclife.course.dto.CourseDTO;
import java.util.List;

public interface CourseService {
    CourseDTO addCourse(CourseDTO courseDTO);
    CourseDTO getCourseById(Long id);
    List<CourseDTO> getAllCourses();
}

