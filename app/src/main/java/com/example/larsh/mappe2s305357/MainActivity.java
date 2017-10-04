package com.example.larsh.mappe2s305357;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);


    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {

        switch (item.getItemId())
        {
            case R.id.addstudent:
                Intent addNewStudent = new Intent(this, NewStudentActivity.class);
                startActivity(addNewStudent);
                break;
            case R.id.deletestudent:
                Intent deleteStudent = new Intent(this, DeleteStudentActivity.class);
                startActivity(deleteStudent);
                break;
            case R.id.changestudent:
                Intent changeStudent = new Intent(this, DeleteStudentActivity.class);
                startActivity(changeStudent);
                break;
            case R.id.sendnewmessage:
                Intent sendNewMessage = new Intent(this,NewMessageActivity.class);
                startActivity(sendNewMessage);
                break;
            case R.id.preferences:
                Intent changePreferences = new Intent(this,SettingsActivity.class);
                startActivity(changePreferences);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
