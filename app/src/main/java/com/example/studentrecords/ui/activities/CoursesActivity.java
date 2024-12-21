package com.example.studentrecords.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrecords.R;
import com.example.studentrecords.data.datasources.databases.DatabaseHelper;
import com.example.studentrecords.data.models.Course;
import com.example.studentrecords.data.repositories.CoursesRepository;
import com.example.studentrecords.domain.usecases.implementation.CoursesUseCase;
import com.example.studentrecords.ui.adapters.CoursesAdapter;

public class CoursesActivity extends AppCompatActivity {
    private CoursesAdapter courseAdapter;
    private CoursesUseCase courseUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        RecyclerView recyclerView = findViewById(R.id.rvCourses);
        Button btnAddCourse = findViewById(R.id.btnAddCourse);
        Button btnAssignStudent = findViewById(R.id.btnAssignStudent);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        courseUseCase = new CoursesUseCase(new CoursesRepository(dbHelper));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CoursesAdapter(courseUseCase.getAllCourses(), new CoursesAdapter.OnCourseClickListener() {
            @Override
            public void onDeleteClick(long courseId) {
                if (courseUseCase.deleteCourse(courseId)) {
                    Toast.makeText(CoursesActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
                    courseAdapter.setCourses(courseUseCase.getAllCourses());
                } else {
                    Toast.makeText(CoursesActivity.this, "Error deleting course", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCourseClick(Course course) {
                String courseDetails = "Course ID: " + course.getId() + "\n" +
                        "Course Name: " + course.getCourseName() + "\n" +
                        "Assigned Student ID: " + (course.getStudentId() != null ? course.getStudentId() : "None");

                new AlertDialog.Builder(CoursesActivity.this)
                        .setTitle("Course Details")
                        .setMessage(courseDetails)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
        recyclerView.setAdapter(courseAdapter);

        btnAddCourse.setOnClickListener(v -> {
            Intent intent = new Intent(CoursesActivity.this, AddCourseActivity.class);
            startActivity(intent);
        });

        btnAssignStudent.setOnClickListener(v -> {
            Intent intent = new Intent(CoursesActivity.this, AssignStudentActivity.class);
            startActivity(intent);
        });
    }
}