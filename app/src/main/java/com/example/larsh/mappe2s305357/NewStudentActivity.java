package com.example.larsh.mappe2s305357;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class NewStudentActivity extends AppCompatActivity
{

    EditText fornavninn;
    EditText etternavninn;
    EditText telefoninn;
    TextView utskrift;
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
        telefoninn= (EditText) findViewById(R.id.telefon);
        utskrift = (TextView) findViewById(R.id.utskrift);
        db = new DBHandler(this);

    }

    public void leggtil(View v )
    {
        Student kontakt = new Student(fornavninn.getText().toString(),etternavninn.getText().toString(),telefoninn.getText().toString());
        db.addStudent(kontakt);
        Log.d("Legg inn: ", "legger til kontakter");
        finish();
    }

    public void visalle(View v)
    {
        List<Student> kontakter = db.findAllStudents();
        String tekst ="";

        for (Student kontakt : kontakter)
        {
            tekst = tekst + "Id: " + kontakt.get_ID() + ",Fornavn: " + kontakt.getFirstname() + ",Etternavn: " + kontakt.getLastName() + " ,Telefon: " + kontakt.getPhonenumber();
            Log.d("Navn: ", tekst);
        }
        utskrift.setText(tekst);
    }
}
