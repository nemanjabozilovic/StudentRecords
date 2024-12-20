package com.example.studentrecords.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrecords.R;
import com.example.studentrecords.data.datasources.databases.DatabaseHelper;
import com.example.studentrecords.data.repositories.CoursesRepository;
import com.example.studentrecords.domain.usecases.implementation.CoursesUseCase;
import com.example.studentrecords.ui.adapters.CoursesAdapter;


public class CoursesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CoursesAdapter courseAdapter;
    private CoursesUseCase courseUseCase;
    private Button btnAddCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        recyclerView = findViewById(R.id.rvCourses);
        btnAddCourse = findViewById(R.id.btnAddCourse);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        courseUseCase = new CoursesUseCase(new CoursesRepository(dbHelper));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter = new CoursesAdapter(courseUseCase.getAllCourses(), courseId -> {
            if (courseUseCase.deleteCourse(courseId)) {
                Toast.makeText(CoursesActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
                courseAdapter.setCourses(courseUseCase.getAllCourses());
            } else {
                Toast.makeText(CoursesActivity.this, "Error deleting course", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(courseAdapter);

        btnAddCourse.setOnClickListener(v -> {
            Intent intent = new Intent(CoursesActivity.this, AddCourseActivity.class);
            startActivity(intent);
        });
    }
}