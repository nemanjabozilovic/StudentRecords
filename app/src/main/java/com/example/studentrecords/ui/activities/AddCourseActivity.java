package com.example.studentrecords.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentrecords.R;
import com.example.studentrecords.data.datasources.databases.DatabaseHelper;
import com.example.studentrecords.data.models.Course;
import com.example.studentrecords.data.repositories.CoursesRepository;
import com.example.studentrecords.domain.usecases.implementation.CoursesUseCase;

import java.util.Objects;

public class AddCourseActivity extends AppCompatActivity {
    private EditText etCourseName;
    private CoursesUseCase courseUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initializeUIElements();
        setUpListeners();
    }

    private void initializeUIElements() {
        etCourseName = findViewById(R.id.etCourseName);
    }

    private void setUpListeners() {
        Button btnSave = findViewById(R.id.btnSave);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        CoursesRepository coursesRepository = new CoursesRepository(dbHelper);
        courseUseCase = new CoursesUseCase(coursesRepository);

        btnSave.setOnClickListener(v -> {
            String courseName = etCourseName.getText().toString().trim();

            if (courseName.isEmpty()) {
                Toast.makeText(AddCourseActivity.this, "Please enter course name", Toast.LENGTH_SHORT).show();
                return;
            }

            Course course = new Course(0, courseName, null);
            long courseId = courseUseCase.addCourse(course);

            if (courseId != -1) {
                Toast.makeText(AddCourseActivity.this, "Course added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddCourseActivity.this, CoursesActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AddCourseActivity.this, "Error adding course", Toast.LENGTH_SHORT).show();
            }
        });
    }
}