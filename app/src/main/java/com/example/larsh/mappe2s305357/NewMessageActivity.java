package com.example.larsh.mappe2s305357;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
    String sendingMessageText = null;
    EditText typedMessageText;
    TextView utskrift;

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
        utskrift = (TextView) findViewById(R.id.utskrift);


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

        String date = null;

        Calendar savingDate = Calendar.getInstance();
        int messageDate = savingDate.get(Calendar.DATE);
        int messageMonth = savingDate.get(Calendar.MONTH);
        int messageYear = savingDate.get(Calendar.YEAR);
        int messageHour = savingDate.get(Calendar.HOUR_OF_DAY);
        int messageMinute = savingDate.get(Calendar.MINUTE);

        date = messageDate + "/" + messageMonth + "-" + messageYear + ", " + messageHour + ":" + messageMinute;
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

    public void visalle( View v )
    {

        List<Message> kontakter = db_MSG.findAllMessages();
        String tekst = "";

        for (Message kontakt : kontakter)
        {
            tekst = tekst + "ID: " + kontakt.getMessage_ID() + "NR: " + kontakt.getMessagePhonenumber() + "STATUS: " + kontakt.getMessageStatus() + "DATE: " + kontakt.getMessageDate() + "MSG: " + kontakt.getMessageMessage();
            Log.d("Navn: ", tekst);
        }
        utskrift.setText(tekst);
    }

    @Override
    public void onDateSet( DatePicker datePicker, int i, int i1, int i2 )
    {

        scheduledYear = i;
        // Must add 1 to show the correct value in months
        scheduledMonth = i1 + 1;
        scheduledDay = i2;
        datePickerReady = true;
        preparesendSMSLater();
    }


    @Override
    public void onTimeSet( TimePicker timePicker, int i, int i1 )
    {

        Log.i("TimePicker", i + "," + i1);
        scheduledHour = i;
        scheduledMinute = i1;
        timePickerReady = true;
        preparesendSMSLater();
    }

    public void preparesendSMSLater( )
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
        } catch (Exception e)
        {
            Toast.makeText(NewMessageActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


}