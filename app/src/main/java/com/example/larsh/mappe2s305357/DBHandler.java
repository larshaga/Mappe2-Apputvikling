package com.example.larsh.mappe2s305357;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper
{

    static String TABLE_STUDENT = "Studenter";
    static String KEY_ID = "_ID";
    static String KEY_FIRSTNAME = "Fornavn";
    static String KEY_LASTNAME = "Etternavn";
    static String KEY_PHONENUMBER = "Telefon";
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Studentkontakt";

    public DBHandler( Context context )
    {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate( SQLiteDatabase studentDB )
    {

        String LAG_TABELL = "CREATE TABLE " + TABLE_STUDENT + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRSTNAME + " TEXT," + KEY_LASTNAME + " TEXT," + KEY_PHONENUMBER + " INTEGER" + ")";
        Log.d("SQL", LAG_TABELL);
        studentDB.execSQL(LAG_TABELL);
    }

    @Override
    public void onUpgrade( SQLiteDatabase studentDB, int i, int i1 )
    {

        studentDB.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(studentDB);
    }

    public void addStudent( Student student )
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, student.getFirstName());
        values.put(KEY_LASTNAME, student.getLastName());
        values.put(KEY_PHONENUMBER, student.getPhonenumber());

        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

}