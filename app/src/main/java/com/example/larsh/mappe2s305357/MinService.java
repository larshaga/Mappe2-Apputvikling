package com.example.larsh.mappe2s305357;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MinService extends Service
{

    @Nullable
    @Override
    public IBinder onBind( Intent intent )
    {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        Toast.makeText(getApplicationContext(), "In MinService",Toast.LENGTH_SHORT);
        return super.onStartCommand(intent,flags,startId);
    }
}
