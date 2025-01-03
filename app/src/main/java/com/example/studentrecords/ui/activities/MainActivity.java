package com.example.studentrecords.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentrecords.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button btnStudents = findViewById(R.id.btnStudents);
        Button btnCourses = findViewById(R.id.btnCourses);

        btnStudents.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentsActivity.class);
            startActivity(intent);
        });

        btnCourses.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
            startActivity(intent);
        });
    }
}
