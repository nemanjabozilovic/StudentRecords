package com.example.studentrecords.ui.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentrecords.R;
import com.example.studentrecords.data.datasources.databases.DatabaseHelper;
import com.example.studentrecords.data.models.Course;
import com.example.studentrecords.data.models.Student;
import com.example.studentrecords.data.repositories.CoursesRepository;
import com.example.studentrecords.data.repositories.StudentsRepository;
import com.example.studentrecords.domain.usecases.implementation.CoursesUseCase;
import com.example.studentrecords.domain.usecases.implementation.StudentsUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignStudentActivity extends AppCompatActivity {
    private Spinner spinnerStudents;
    private Spinner spinnerCourses;
    private Button btnAssign;
    private StudentsUseCase studentsUseCase;
    private CoursesUseCase coursesUseCase;
    private Map<String, Long> studentNameToIdMap;
    private Map<String, Long> courseNameToIdMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_student);

        spinnerStudents = findViewById(R.id.spinnerStudents);
        spinnerCourses = findViewById(R.id.spinnerCourses);
        btnAssign = findViewById(R.id.btnAssign);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        studentsUseCase = new StudentsUseCase(new StudentsRepository(dbHelper));
        coursesUseCase = new CoursesUseCase(new CoursesRepository(dbHelper));

        studentNameToIdMap = new HashMap<>();
        courseNameToIdMap = new HashMap<>();

        populateSpinners();

        btnAssign.setOnClickListener(v -> {
            String selectedStudentName = (String) spinnerStudents.getSelectedItem();
            String selectedCourseName = (String) spinnerCourses.getSelectedItem();

            if (selectedStudentName != null && selectedCourseName != null) {
                long studentId = studentNameToIdMap.get(selectedStudentName);
                long courseId = courseNameToIdMap.get(selectedCourseName);

                if (coursesUseCase.assignStudentToCourse(courseId, studentId)) {
                    Toast.makeText(AssignStudentActivity.this, "Student assigned to course", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AssignStudentActivity.this, "Error assigning student to course", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AssignStudentActivity.this, "Please select both a student and a course.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateSpinners() {
        List<Student> students = studentsUseCase.getAllStudents();
        List<Course> courses = coursesUseCase.getAllCourses();

        ArrayAdapter<String> studentsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getStudentNames(students)
        );

        studentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudents.setAdapter(studentsAdapter);

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                getCourseNames(courses)
        );
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(coursesAdapter);
    }

    private List<String> getStudentNames(List<Student> students) {
        studentNameToIdMap.clear();
        List<String> names = new ArrayList<>();
        for (Student student : students) {
            String fullName = student.getFirstName() + " " + student.getLastName();
            studentNameToIdMap.put(fullName, student.getId());
            names.add(fullName);
        }
        return names;
    }

    private List<String> getCourseNames(List<Course> courses) {
        courseNameToIdMap.clear();
        List<String> names = new ArrayList<>();
        for (Course course : courses) {
            courseNameToIdMap.put(course.getCourseName(), course.getId());
            names.add(course.getCourseName());
        }
        return names;
    }
}