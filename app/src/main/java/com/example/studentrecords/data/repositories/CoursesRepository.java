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
    private final DatabaseHelper dbHelper;

    public CoursesRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private Course extractCourseFromCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("id");
        int courseNameIndex = cursor.getColumnIndex("course_name");

        if (idIndex >= 0 && courseNameIndex >= 0) {
            long id = cursor.getLong(idIndex);
            String courseName = cursor.getString(courseNameIndex);

            return new Course(id, courseName);
        }
        return null;
    }

    @Override
    public long addCourse(Course course) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("course_name", course.getCourseName());

        long id = db.insert("courses", null, values);
        db.close();
        return id;
    }

    @Override
    public Course getCourseById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "courses",
                null,
                "id = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        Course course = null;
        if (cursor != null && cursor.moveToFirst()) {
            course = extractCourseFromCursor(cursor);
            cursor.close();
        }

        db.close();
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("courses", null, null, null, null, null, null);

        List<Course> courses = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Course course = extractCourseFromCursor(cursor);
                if (course != null) {
                    courses.add(course);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return courses;
    }

    @Override
    public boolean updateCourse(Course course) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("course_name", course.getCourseName());

        int rowsUpdated = db.update(
                "courses",
                values,
                "id = ?",
                new String[]{String.valueOf(course.getId())}
        );

        db.close();
        return rowsUpdated > 0;
    }

    @Override
    public boolean deleteCourse(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rowsDeleted = db.delete(
                "courses",
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return rowsDeleted > 0;
    }
}