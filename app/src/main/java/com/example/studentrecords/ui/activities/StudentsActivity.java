package com.example.studentrecords.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrecords.R;
import com.example.studentrecords.data.datasources.databases.DatabaseHelper;
import com.example.studentrecords.data.models.Student;
import com.example.studentrecords.data.repositories.StudentsRepository;
import com.example.studentrecords.domain.usecases.implementation.StudentsUseCase;
import com.example.studentrecords.ui.adapters.StudentsAdapter;

import java.util.Collections;
import java.util.List;

public class StudentsActivity extends AppCompatActivity {
    private StudentsAdapter studentAdapter;
    private StudentsUseCase studentUseCase;
    private boolean isSortedByGPA = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        RecyclerView recyclerView = findViewById(R.id.rvStudents);
        Button btnAddStudent = findViewById(R.id.btnAddStudent);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        studentUseCase = new StudentsUseCase(new StudentsRepository(dbHelper));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentsAdapter(studentUseCase.getAllStudents(), new StudentsAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(Student student) {
                String studentDetails = "ID: " + student.getId() + "\n" +
                        "Name: " + student.getFullName() + "\n" +
                        "Date of Birth: " + student.getDateOfBirth() + "\n" +
                        "Email: " + student.getEmail() + "\n" +
                        "Department: " + student.getDepartment() + "\n" +
                        "GPA: " + student.getGpa();

                new AlertDialog.Builder(StudentsActivity.this)
                        .setTitle("Student Details")
                        .setMessage(studentDetails)
                        .setPositiveButton("OK", null)
                        .show();
            }

            @Override
            public void onDeleteClick(long studentId) {
                if (studentUseCase.deleteStudent(studentId)) {
                    Toast.makeText(StudentsActivity.this, "Student deleted", Toast.LENGTH_SHORT).show();
                    studentAdapter.setStudents(studentUseCase.getAllStudents());
                } else {
                    Toast.makeText(StudentsActivity.this, "Error deleting student", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(studentAdapter);

        btnAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(StudentsActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            sortByGPA();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortByGPA() {
        List<Student> students = studentUseCase.getAllStudents();

        if (isSortedByGPA) {
            studentAdapter.setStudents(students);
            isSortedByGPA = false;
        } else {
            Collections.sort(students, (s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()));
            studentAdapter.setStudents(students);
            isSortedByGPA = true;
        }
    }
}