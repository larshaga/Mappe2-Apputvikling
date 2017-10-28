package com.example.larsh.mappe2s305357;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final Switch weeklyMessagesSwitch = (Switch) findViewById(R.id.weeklyMSGSwitch);
        final EditText weeklyMessagesText = (EditText) findViewById(R.id.weeklyMessageText);

        weeklyMessagesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged( CompoundButton compoundButton, boolean b )
            {

                if (b)
                {
                    weeklyMessagesText.setEnabled(true);
                }
                else if (!b)
                {
                    weeklyMessagesText.setEnabled(false);
                }
            }
        });

    }

}
