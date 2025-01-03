package com.example.studentrecords.data.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentrecords.data.datasources.databases.DatabaseHelper;
import com.example.studentrecords.data.models.Course;
import com.example.studentrecords.domain.repositories.ICoursesRepository;

import java.util.ArrayList;
import java.util.List;

public class CoursesRepository implements ICoursesRepository {
    private static final String TABLE_COURSES = "courses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_STUDENT_ID = "student_id";

    private final DatabaseHelper dbHelper;

    public CoursesRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private Course extractCourseFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
        String courseName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE_NAME));
        Long studentId = cursor.isNull(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID))
                ? null
                : cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID));

        return new Course(id, courseName, studentId);
    }

    @Override
    public long addCourse(Course course) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_COURSE_NAME, course.getCourseName());
            values.put(COLUMN_STUDENT_ID, course.getStudentId());
            return db.insert(TABLE_COURSES, null, values);
        }
    }

    @Override
    public Course getCourseById(long id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     TABLE_COURSES,
                     null,
                     COLUMN_ID + " = ?",
                     new String[]{String.valueOf(id)},
                     null, null, null)) {

            if (cursor.moveToFirst()) {
                return extractCourseFromCursor(cursor);
            }
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(TABLE_COURSES, null, null, null, null, null, null)) {

            while (cursor.moveToNext()) {
                courses.add(extractCourseFromCursor(cursor));
            }
        }
        return courses;
    }

    @Override
    public boolean updateCourse(Course course) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_COURSE_NAME, course.getCourseName());
            values.put(COLUMN_STUDENT_ID, course.getStudentId());

            int rowsUpdated = db.update(
                    TABLE_COURSES,
                    values,
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(course.getId())}
            );

            return rowsUpdated > 0;
        }
    }

    @Override
    public boolean deleteCourse(long id) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            int rowsDeleted = db.delete(
                    TABLE_COURSES,
                    COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)}
            );

            return rowsDeleted > 0;
        }
    }
}