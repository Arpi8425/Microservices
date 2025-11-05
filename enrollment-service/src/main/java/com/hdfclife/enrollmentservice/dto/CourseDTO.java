package com.hdfclife.enrollmentservice.dto;

public class CourseDTO {
    private Long id;
    private String courseName;
    private String duration;

    public CourseDTO() {}

    public CourseDTO(Long id, String courseName, String duration) {
        this.id = id;
        this.courseName = courseName;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
