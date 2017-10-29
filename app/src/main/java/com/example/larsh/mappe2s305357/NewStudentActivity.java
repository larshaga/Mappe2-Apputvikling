package com.example.larsh.mappe2s305357;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NewStudentActivity extends AppCompatActivity
{

    EditText fornavninn;
    EditText etternavninn;
    EditText telefoninn;
    DBHandler db;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        fornavninn = (EditText) findViewById(R.id.fornavn);
        etternavninn = (EditText) findViewById(R.id.etternavn);
        telefoninn = (EditText) findViewById(R.id.telefon);
        db = new DBHandler(this);

    }

    public void leggtil( View v )
    {

        if (fornavninn.getText().length() == 0 || etternavninn.getText().length() == 0 || telefoninn.getText().length() != 8)
        {
            Toast.makeText(this, "Check your info and try again, all fields needs to be filled out and phonenumber needs to be 8 characters.", Toast.LENGTH_LONG).show();
            return;
        }

        Student kontakt = new Student(fornavninn.getText().toString(), etternavninn.getText().toString(), telefoninn.getText().toString());
        db.addStudent(kontakt);
        finish();

    }
}
