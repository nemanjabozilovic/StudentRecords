package com.example.studentrecords.domain.usecases.interfaces;

import com.example.studentrecords.data.models.Course;

import java.util.List;

public interface ICoursesUseCase {
    long addCourse(Course course);
    List<Course> getAllCourses();
    boolean deleteCourse(long id);
    Course getCourseById(long id);
    boolean updateCourse(Course course);
    boolean assignStudentToCourse(long courseId, long studentId);
}
