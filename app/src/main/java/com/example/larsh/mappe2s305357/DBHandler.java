package com.example.larsh.mappe2s305357;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
        values.put(KEY_FIRSTNAME, student.getFirstname());
        values.put(KEY_LASTNAME, student.getLastName());
        values.put(KEY_PHONENUMBER, student.getPhonenumber());

        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    public List<Student> findAllStudents( )
    {

        List<Student> kontaktListe = new ArrayList<Student>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENT + " ORDER BY " + KEY_LASTNAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Student kontakt = new Student();
                kontakt.set_ID(cursor.getLong(0));
                kontakt.setFirstname(cursor.getString(1));
                kontakt.setLastName(cursor.getString(2));
                kontakt.setPhonenumber(cursor.getString(3));
                kontaktListe.add(kontakt);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return kontaktListe;
    }


    public void slettKontaktString( String studentFirstName, String studentLastName )
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, KEY_FIRSTNAME + "='" + studentFirstName + "' AND " + KEY_LASTNAME + " = '" + studentLastName + "'", new String[]{ });
        db.close();
    }

    public List<Student> findAStudent( String studentFirstName, String studentLastName )
    {

        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENT + " WHERE " + KEY_FIRSTNAME + "='" + studentFirstName + "' AND " + KEY_LASTNAME + " = '" + studentLastName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Student studentInfo = new Student();
                studentInfo.set_ID(cursor.getLong(0));
                studentInfo.setFirstname(cursor.getString(1));
                studentInfo.setLastName(cursor.getString(2));
                studentInfo.setPhonenumber(cursor.getString(3));
                studentList.add(studentInfo);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return studentList;
    }


    public int oppdaterKontakt( Student kontakt )
    {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, kontakt.getFirstname());
        values.put(KEY_LASTNAME, kontakt.getLastName());
        values.put(KEY_PHONENUMBER, kontakt.getPhonenumber());
        int endret = db.update(TABLE_STUDENT, values, KEY_ID + "= ?", new String[]{String.valueOf(kontakt.get_ID())});
        db.close();

        return endret;
    }

}