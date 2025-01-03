package com.example.studentrecords.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentrecords.R;
import com.example.studentrecords.data.datasources.databases.DatabaseHelper;
import com.example.studentrecords.data.models.Student;
import com.example.studentrecords.data.repositories.StudentsRepository;
import com.example.studentrecords.domain.usecases.implementation.StudentsUseCase;

import java.util.Objects;

public class AddStudentActivity extends AppCompatActivity {
    private EditText etFirstName, etLastName, etEmail, etDateOfBirth, etDepartment, etGPA;
    private StudentsUseCase studentsUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initializeUIElements();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        StudentsRepository studentsRepository = new StudentsRepository(dbHelper);
        studentsUseCase = new StudentsUseCase(studentsRepository);

        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String dateOfBirth = etDateOfBirth.getText().toString().trim();
            String department = etDepartment.getText().toString().trim();
            double gpa = 0.0;

            try {
                gpa = Double.parseDouble(etGPA.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(AddStudentActivity.this, "Invalid GPA format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dateOfBirth.isEmpty() || department.isEmpty()) {
                Toast.makeText(AddStudentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Student student = new Student(0, firstName, lastName, dateOfBirth, email, department, gpa);

            long studentId = studentsUseCase.addStudent(student);

            if (studentId != -1) {
                Toast.makeText(AddStudentActivity.this, "Student added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddStudentActivity.this, StudentsActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AddStudentActivity.this, "Error adding student", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeUIElements() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etDepartment = findViewById(R.id.etDepartment);
        etGPA = findViewById(R.id.etGPA);
    }
}