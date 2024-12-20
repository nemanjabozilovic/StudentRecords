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
import com.example.studentrecords.data.repositories.StudentsRepository;
import com.example.studentrecords.domain.usecases.implementation.StudentsUseCase;
import com.example.studentrecords.ui.adapters.StudentsAdapter;

public class StudentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentsAdapter studentAdapter;
    private StudentsUseCase studentUseCase;
    private Button btnAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        recyclerView = findViewById(R.id.rvStudents);
        btnAddStudent = findViewById(R.id.btnAddStudent);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        studentUseCase = new StudentsUseCase(new StudentsRepository(dbHelper));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentsAdapter(studentUseCase.getAllStudents(), studentId -> {
            if (studentUseCase.deleteStudent(studentId)) {
                Toast.makeText(StudentsActivity.this, "Student deleted", Toast.LENGTH_SHORT).show();
                studentAdapter.setStudents(studentUseCase.getAllStudents());
            } else {
                Toast.makeText(StudentsActivity.this, "Error deleting student", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(studentAdapter);

        btnAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(StudentsActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });
    }
}