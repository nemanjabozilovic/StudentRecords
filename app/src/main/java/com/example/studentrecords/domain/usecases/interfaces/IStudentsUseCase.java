package com.example.studentrecords.domain.usecases.interfaces;

import com.example.studentrecords.data.models.Student;

import java.util.List;

public interface IStudentsUseCase {
    long addStudent(Student student);
    List<Student> getAllStudents();
    boolean deleteStudent(long id);
    Student getStudentById(long id);
    boolean updateStudent(Student student);
}