package com.example.studentrecords.domain.repositories;

import com.example.studentrecords.data.models.Student;

import java.util.List;

public interface IStudentsRepository {
    long addStudent(Student student);
    Student getStudentById(long id);
    List<Student> getAllStudents();
    boolean updateStudent(Student student);
    boolean deleteStudent(long id);
}
