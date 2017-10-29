package com.example.larsh.mappe2s305357;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive( Context context, Intent intent )
    {

        Intent background = new Intent(context, BackgroundService.class);
        context.startService(background);
    }


}