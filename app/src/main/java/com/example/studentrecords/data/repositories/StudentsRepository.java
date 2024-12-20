package com.example.studentrecords.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentrecords.data.datasources.databases.DatabaseHelper;
import com.example.studentrecords.data.models.Student;
import com.example.studentrecords.domain.repositories.IStudentsRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentsRepository implements IStudentsRepository {
    private final DatabaseHelper dbHelper;

    public StudentsRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public long addStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("first_name", student.getFirstName());
        values.put("last_name", student.getLastName());
        values.put("date_of_birth", student.getDateOfBirth());
        values.put("email", student.getEmail());
        values.put("department", student.getDepartment());
        values.put("gpa", student.getGpa());

        long id = db.insert("students", null, values);
        db.close();
        return id;
    }

    @Override
    public Student getStudentById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "students",
                null,
                "id = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        Student student = null;
        if (cursor != null && cursor.moveToFirst()) {
            student = extractStudentFromCursor(cursor);
            cursor.close();
        }

        db.close();
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("students", null, null, null, null, null, null);

        List<Student> students = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Student student = extractStudentFromCursor(cursor);
                if (student != null) {
                    students.add(student);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return students;
    }

    private Student extractStudentFromCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("id");
        int firstNameIndex = cursor.getColumnIndex("first_name");
        int lastNameIndex = cursor.getColumnIndex("last_name");
        int dateOfBirthIndex = cursor.getColumnIndex("date_of_birth");
        int emailIndex = cursor.getColumnIndex("email");
        int departmentIndex = cursor.getColumnIndex("department");
        int gpaIndex = cursor.getColumnIndex("gpa");

        if (idIndex >= 0 && firstNameIndex >= 0 && lastNameIndex >= 0 && dateOfBirthIndex >= 0 && emailIndex >= 0) {
            long id = cursor.getLong(idIndex);
            String firstName = cursor.getString(firstNameIndex);
            String lastName = cursor.getString(lastNameIndex);
            String dateOfBirth = cursor.getString(dateOfBirthIndex);
            String email = cursor.getString(emailIndex);
            String department = cursor.getString(departmentIndex);
            double gpa = cursor.getDouble(gpaIndex);

            return new Student(id, firstName, lastName, dateOfBirth, email, department, gpa);
        }
        return null;
    }

    @Override
    public boolean updateStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("first_name", student.getFirstName());
        values.put("last_name", student.getLastName());
        values.put("date_of_birth", student.getDateOfBirth());
        values.put("email", student.getEmail());
        values.put("department", student.getDepartment());
        values.put("gpa", student.getGpa());

        int rowsUpdated = db.update(
                "students",
                values,
                "id = ?",
                new String[]{String.valueOf(student.getId())}
        );

        db.close();
        return rowsUpdated > 0;
    }

    @Override
    public boolean deleteStudent(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = db.delete(
                "students",
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return rowsDeleted > 0;
    }
}