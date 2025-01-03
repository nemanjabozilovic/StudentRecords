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
    private static final String TABLE_STUDENTS = "students";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_DEPARTMENT = "department";
    private static final String COLUMN_GPA = "gpa";

    private final DatabaseHelper dbHelper;

    public StudentsRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private Student extractStudentFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
        String dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_OF_BIRTH));
        String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
        String department = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEPARTMENT));
        double gpa = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GPA));

        return new Student(id, firstName, lastName, dateOfBirth, email, department, gpa);
    }

    @Override
    public long addStudent(Student student) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIRST_NAME, student.getFirstName());
            values.put(COLUMN_LAST_NAME, student.getLastName());
            values.put(COLUMN_DATE_OF_BIRTH, student.getDateOfBirth());
            values.put(COLUMN_EMAIL, student.getEmail());
            values.put(COLUMN_DEPARTMENT, student.getDepartment());
            values.put(COLUMN_GPA, student.getGpa());

            return db.insert(TABLE_STUDENTS, null, values);
        }
    }

    @Override
    public Student getStudentById(long id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     TABLE_STUDENTS,
                     null,
                     COLUMN_ID + " = ?",
                     new String[]{String.valueOf(id)},
                     null, null, null)) {

            if (cursor.moveToFirst()) {
                return extractStudentFromCursor(cursor);
            }
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(TABLE_STUDENTS, null, null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                students.add(extractStudentFromCursor(cursor));
            }
        }
        return students;
    }

    @Override
    public boolean updateStudent(Student student) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIRST_NAME, student.getFirstName());
            values.put(COLUMN_LAST_NAME, student.getLastName());
            values.put(COLUMN_DATE_OF_BIRTH, student.getDateOfBirth());
            values.put(COLUMN_EMAIL, student.getEmail());
            values.put(COLUMN_DEPARTMENT, student.getDepartment());
            values.put(COLUMN_GPA, student.getGpa());

            int rowsUpdated = db.update(
                    TABLE_STUDENTS,
                    values,
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(student.getId())}
            );

            return rowsUpdated > 0;
        }
    }

    @Override
    public boolean deleteStudent(long id) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            int rowsDeleted = db.delete(
                    TABLE_STUDENTS,
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)}
            );

            return rowsDeleted > 0;
        }
    }
}