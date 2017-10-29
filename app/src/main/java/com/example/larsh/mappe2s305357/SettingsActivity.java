package com.example.larsh.mappe2s305357;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class SettingsActivity extends AppCompatActivity
{

    DBHandler db;
    DBHandlerMSG db_MSG;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = new DBHandler(this);
        db_MSG = new DBHandlerMSG(this);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final Switch weeklyMessagesSwitch = (Switch) findViewById(R.id.weeklyMSGSwitch);
        final EditText weeklyMessagesText = (EditText) findViewById(R.id.weeklyMessageText);
        final Button scheduleMessages = (Button) findViewById(R.id.btn_schedule);

        weeklyMessagesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged( CompoundButton compoundButton, boolean b )
            {

                if (b)
                {
                    weeklyMessagesText.setEnabled(true);
                    scheduleMessages.setEnabled(true);
                }
                else if (!b)
                {
                    weeklyMessagesText.setEnabled(false);
                    scheduleMessages.setEnabled(false);

                }
            }
        });

        scheduleMessages.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick( View view )
            {

                try
                {
                    final Calendar c = Calendar.getInstance();
                    final int year = c.get(Calendar.YEAR);
                    final int month = c.get(Calendar.MONTH);
                    final int day = c.get(Calendar.DATE);
                    final int hour = c.get(Calendar.HOUR_OF_DAY);
                    final int minute = c.get(Calendar.MINUTE);

                    String date = day + "/" + month + "-" + year+ ", " + hour+ ":" + minute;

                    List<Student> phonenumber = db.findAllStudents();

                    for (Student nummer : phonenumber)
                    {
                        db_MSG.newMessage(nummer.getPhonenumber(), String.valueOf(weeklyMessagesText.getText()), date, "Not sent");
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                PendingIntent alarmIntent = null;

                // Fungerer ikke, da denne kan gi nullPointerException
                alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);

            }
        });
    }
}

