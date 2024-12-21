package com.example.studentrecords.domain.usecases.implementation;

import com.example.studentrecords.data.models.Student;
import com.example.studentrecords.domain.repositories.IStudentsRepository;
import com.example.studentrecords.domain.usecases.interfaces.IStudentsUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class StudentsUseCase implements IStudentsUseCase {
    private final IStudentsRepository studentsRepository;

    public StudentsUseCase(IStudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    @Override
    public long addStudent(Student student) {
        return studentsRepository.addStudent(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentsRepository.getAllStudents();
    }

    @Override
    public boolean deleteStudent(long id) {
        return studentsRepository.deleteStudent(id);
    }

    @Override
    public Student getStudentById(long id) {
        return studentsRepository.getStudentById(id);
    }

    @Override
    public boolean updateStudent(Student student) {
        return studentsRepository.updateStudent(student);
    }

    @Override
    public List<String> getAllStudentNames() {
        List<Student> students = studentsRepository.getAllStudents();
        return students.stream()
                .map(Student::getFullName)
                .collect(Collectors.toList());
    }
}