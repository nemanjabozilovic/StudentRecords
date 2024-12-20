package com.example.studentrecords.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentrecords.R;

public class MainActivity extends AppCompatActivity {
    private Button btnStudents, btnCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStudents = findViewById(R.id.btnStudents);
        btnCourses = findViewById(R.id.btnCourses);

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
