package com.example.larsh.mappe2s305357;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ViewAnimationUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHandlerMSG extends SQLiteOpenHelper
{

    static String TABLE_MSG_MESSAGE = "SentMessages";
    static String KEY_MSG_ID = "_ID";
    static String KEY_MSG_PHONENUMBER = "Phonenumber";
    static String KEY_MSG_MESSAGE = "Message";
    static String KEY_MSG_DATE = "Date";
    static String KEY_MSG_STATUS = "Status";
    static int DATABASE_VERSION = 7;
    static String DATABASE_NAME = "Messages";

    public DBHandlerMSG( Context context )
    {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate( SQLiteDatabase messagesDB )
    {

        String LAG_TABELL = "CREATE TABLE " + TABLE_MSG_MESSAGE + "(" + KEY_MSG_ID + " INTEGER PRIMARY KEY," + KEY_MSG_PHONENUMBER + " TEXT," + KEY_MSG_MESSAGE + " TEXT," + KEY_MSG_DATE + " TEXT," + KEY_MSG_STATUS + " TEXT" + ")";
        Log.d("SQL", LAG_TABELL);
        messagesDB.execSQL(LAG_TABELL);

    }

    @Override
    public void onUpgrade( SQLiteDatabase messagesDB, int i, int i1 )
    {

        messagesDB.execSQL("DROP TABLE IF EXISTS " + TABLE_MSG_MESSAGE);
        onCreate(messagesDB);
    }

    public void newMessage( String phonenumber, String message, String date, String status )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MSG_PHONENUMBER, phonenumber);
        values.put(KEY_MSG_MESSAGE, message);
        values.put(KEY_MSG_DATE,date);
        values.put(KEY_MSG_STATUS, status);
        db.insert(TABLE_MSG_MESSAGE, null, values);
        db.close();
    }

    public List<Message> findAllMessages( )
    {

        List<Message> listAllMessages = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MSG_MESSAGE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
                Message sentMessage = new Message();
                sentMessage.setMessage_ID(cursor.getLong(0));
                sentMessage.setMessagePhonenumber(cursor.getString(1));
                sentMessage.setMessageMessage(cursor.getString(2));
                sentMessage.setMessageDate(cursor.getString(3));
                sentMessage.setMessageStatus(cursor.getString(4));
                listAllMessages.add(sentMessage);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return listAllMessages;
    }
}
