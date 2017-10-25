package com.example.larsh.mappe2s305357;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class NewMessageActivity extends AppCompatActivity
{

    DBHandler db;
    DBHandlerMSG db_MSG;
    Button visAlle;
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

        Button sendNewMessage = (Button) findViewById(R.id.btn_sendMessage);
        final EditText messageText = (EditText) findViewById(R.id.newMessageText);
        visAlle = (Button) findViewById(R.id.visalle);
        utskrift = (TextView) findViewById(R.id.utskrift);
        visAlle.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick( View view )
            {
                List<Message> testing = db_MSG.findAllMessages();

                String tekst = "";

                for (Message kontakt : testing)
                {
                    tekst = tekst + "Id: " + kontakt.getMessage_ID() + ", " + kontakt.getMessagePhonenumber() + ", " + kontakt.getMessageMessage() + ", " + kontakt.getMessageDate() + ", " + kontakt.getMessageStatus();
                    Log.d("Test: ", tekst);
                }
                utskrift.setText(tekst);
            }
        });

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

        Calendar savingDate = Calendar.getInstance();
        int messageDate = savingDate.get(Calendar.DATE);
        int messageMonth = savingDate.get(Calendar.MONTH);
        int messageYear = savingDate.get(Calendar.YEAR);
        int messageHour = savingDate.get(Calendar.HOUR_OF_DAY);
        int messageMinute = savingDate.get(Calendar.MINUTE);
        int messageMillis = savingDate.get(Calendar.MILLISECOND);

        String status = "Not sent";
        String date = messageDate + "/" + messageMonth + "-" + messageYear + ", " + messageHour + ":" + messageMinute + ":" + messageMillis;

        try
        {
            Log.i("Phonenumber", phoneNumber);
            Log.i("Message", message);
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(NewMessageActivity.this, "Message sent", Toast.LENGTH_SHORT).show();

            db_MSG.newMessage(phoneNumber,message,date,status);
        } catch (Exception e)
        {
            Toast.makeText(NewMessageActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
