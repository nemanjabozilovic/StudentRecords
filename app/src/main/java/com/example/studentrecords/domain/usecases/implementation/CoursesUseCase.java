package com.example.studentrecords.domain.usecases.implementation;

import com.example.studentrecords.data.models.Course;
import com.example.studentrecords.domain.repositories.ICoursesRepository;
import com.example.studentrecords.domain.usecases.interfaces.ICoursesUseCase;

import java.util.List;

public class CoursesUseCase implements ICoursesUseCase {
    private final ICoursesRepository coursesRepository;

    public CoursesUseCase(ICoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    @Override
    public long addCourse(Course course) {
        return coursesRepository.addCourse(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return coursesRepository.getAllCourses();
    }

    @Override
    public boolean deleteCourse(long id) {
        return coursesRepository.deleteCourse(id);
    }

    @Override
    public Course getCourseById(long id) {
        return coursesRepository.getCourseById(id);
    }

    @Override
    public boolean updateCourse(Course course) {
        return coursesRepository.updateCourse(course);
    }

    @Override
    public boolean assignStudentToCourse(long courseId, long studentId) {
        Course course = coursesRepository.getCourseById(courseId);
        if (course == null) {
            return false;
        }
        course.setStudentId(studentId);

        return coursesRepository.updateCourse(course);
    }
}
