package com.example.larsh.mappe2s305357;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class NewMessageActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{

    DBHandler db;
    DBHandlerMSG db_MSG;
    int scheduledYear = -1;
    int scheduledMonth;
    int scheduledDay;
    int scheduledHour = -1;
    int scheduledMinute;
    boolean timePickerReady = false;
    boolean datePickerReady = false;
    EditText typedMessageText;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        db = new DBHandler(this);
        db_MSG = new DBHandlerMSG(NewMessageActivity.this);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button sendNewMessageNow = (Button) findViewById(R.id.btn_sendMessageNow);
        Button sendNewMessageLater = (Button) findViewById(R.id.btn_sendMessageLater);
        typedMessageText = (EditText) findViewById(R.id.newMessageText);


        // Gets the date of today
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DATE);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);


        sendNewMessageNow.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick( View view )
            {

                List<Student> phonenumber = db.findAllStudents();

                for (Student kontakt : phonenumber)
                {
                    sendSMSNow(kontakt.getPhonenumber(), String.valueOf(typedMessageText.getText()));
                }


            }
        });

        sendNewMessageLater.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick( View v )
            {

                final DatePickerDialog datePickerDialog = new DatePickerDialog(NewMessageActivity.this, NewMessageActivity.this, year, month, day);
                final TimePickerDialog timePickerDialog = new TimePickerDialog(NewMessageActivity.this, NewMessageActivity.this, hour, minute, true);

                // Asks the user to pick a date and time the user wants to send the message
                timePickerDialog.show();
                datePickerDialog.show();


            }
        });

    }

    public void sendSMSNow( String phoneNumber, String message )
    {

        Calendar savingDate = Calendar.getInstance();
        int messageDate = savingDate.get(Calendar.DATE);
        int messageMonth = savingDate.get(Calendar.MONTH);
        int messageYear = savingDate.get(Calendar.YEAR);
        int messageHour = savingDate.get(Calendar.HOUR_OF_DAY);
        int messageMinute = savingDate.get(Calendar.MINUTE);

        String date = messageDate + "/" + messageMonth + "-" + messageYear + ", " + messageHour + ":" + messageMinute;
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            db_MSG.newMessage(phoneNumber, message, date, "Sent");

            Toast.makeText(NewMessageActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            Toast.makeText(NewMessageActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onDateSet( DatePicker datePicker, int i, int i1, int i2 )
    {

        scheduledYear = i;
        // Must add 1 to show the correct value in months
        scheduledMonth = i1 + 1;
        scheduledDay = i2;
        datePickerReady = true;
        prepareSendSMSLater();
    }


    @Override
    public void onTimeSet( TimePicker timePicker, int i, int i1 )
    {

        Log.i("TimePicker", i + "," + i1);
        scheduledHour = i;
        scheduledMinute = i1;
        timePickerReady = true;
        prepareSendSMSLater();
    }

    public void prepareSendSMSLater( )
    {

        if (datePickerReady && timePickerReady)
        {
            Log.i("typedMessageText", String.valueOf(typedMessageText.getText()));

            String date = scheduledDay + "/" + scheduledMonth + "-" + scheduledYear + ", " + scheduledHour + ":" + scheduledMinute;

            List<Student> phonenumber = db.findAllStudents();

            for (Student kontakt : phonenumber)
            {
                sendSMSLater(kontakt.getPhonenumber(), date);
            }
        }
    }

    public void sendSMSLater( String phoneNumber, String date )
    {

        try
        {
            db_MSG.newMessage(phoneNumber, String.valueOf(typedMessageText.getText()), date, "Not sent");

            Toast.makeText(NewMessageActivity.this, "Message scheduled for " + date, Toast.LENGTH_SHORT).show();

            // Her skal egentlig AlarmManageren være.
            // Den skal gå igjennom databasen og søke etter linjer som har status "ikke sent", så skal den se hvor lang tid det er til denne datoen og schedule en bakgrunnsprosess, slik at meldingene blir sent på riktig tidspunkt.

        } catch (Exception e)
        {
            Toast.makeText(NewMessageActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}