package com.example.larsh.mappe2s305357;

import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.Calendar;


public class BackgroundService extends Service
{

    DBHandler db;
    DBHandlerMSG db_MSG;
    Context context;

    @Nullable
    @Override
    public IBinder onBind( Intent intent )
    {

        return null;
    }

    @Override
    public void onCreate( )
    {

        this.context = this;
        db = new DBHandler(this);
        db_MSG = new DBHandlerMSG(this);

        // Klarer ikke få taki telefonnummeret eller meldingen som skal sendes.
        //sendSMSNow();


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

            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
