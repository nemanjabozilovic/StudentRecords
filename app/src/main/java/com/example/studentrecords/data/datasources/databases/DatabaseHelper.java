package com.example.studentrecords.data.datasources.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name and Version
    private static final String DATABASE_NAME = "student_records.db";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_COURSES = "courses";

    // Common Column Names
    private static final String COLUMN_ID = "id";

    // Students Table Columns
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_DEPARTMENT = "department";
    private static final String COLUMN_GPA = "gpa";

    // Courses Table Columns
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_STUDENT_ID = "student_id";

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
            COLUMN_LAST_NAME + " TEXT NOT NULL, " +
            COLUMN_DATE_OF_BIRTH + " TEXT NOT NULL, " +
            COLUMN_EMAIL + " TEXT NOT NULL, " +
            COLUMN_DEPARTMENT + " TEXT, " +
            COLUMN_GPA + " REAL" + ");";

    private static final String CREATE_TABLE_COURSES = "CREATE TABLE " + TABLE_COURSES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_COURSE_NAME + " TEXT NOT NULL, " +
            COLUMN_STUDENT_ID + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_STUDENT_ID + ") REFERENCES " +
            TABLE_STUDENTS + "(" + COLUMN_ID + ")" + " ON DELETE SET NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_COURSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }
}