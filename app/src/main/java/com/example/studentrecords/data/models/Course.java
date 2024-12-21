package com.example.studentrecords.data.models;

public class Course {
    private long id;
    private String courseName;
    private Long studentId;

    public Course(long id, String courseName, Long studentId) {
        this.id = id;
        this.courseName = courseName;
        this.studentId = studentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}