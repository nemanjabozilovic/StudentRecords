package com.example.studentrecords.domain.repositories;

import com.example.studentrecords.data.models.Course;

import java.util.List;

public interface ICoursesRepository {
    long addCourse(Course course);
    Course getCourseById(long id);
    List<Course> getAllCourses();
    boolean updateCourse(Course course);
    boolean deleteCourse(long id);
}
