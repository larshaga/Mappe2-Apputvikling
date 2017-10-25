package com.example.larsh.mappe2s305357;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class NewMessageActivity extends AppCompatActivity
{

    DBHandler db;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        db = new DBHandler(this);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button sendNewMessage = (Button) findViewById(R.id.btn_sendMessage);
        final EditText messageText = (EditText) findViewById(R.id.newMessageText);

        sendNewMessage.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick( View view )
            {

                List<Student> phonenumber = db.findAllStudents();

                for (Student kontakt : phonenumber)
                {
                    Log.i("Sending message", "Sending message: " + String.valueOf(messageText.getText()) + ", To phonenumber: " + kontakt.getPhonenumber());
                    sendSMS(kontakt.getPhonenumber(), String.valueOf(messageText.getText()));
                }


            }
        });
    }


    public void sendSMS( String phoneNumber, String message )
    {

        try
        {
            Log.i("Phonenumber", phoneNumber);
            Log.i("Message", message);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(NewMessageActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            Toast.makeText(NewMessageActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
