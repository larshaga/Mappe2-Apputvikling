package com.example.larsh.mappe2s305357;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NewStudentActivity extends AppCompatActivity
{

    EditText fornavninn;
    EditText etternavninn;
    EditText telefoninn;
    TextView utskrift;
    DBHandler db;

    String fornavn;
    String etternavn;
    String telefon;

    public String getFornavn( )
    {

        return fornavn;
    }

    public void setFornavn( String fornavn )
    {

        this.fornavn = fornavn;
    }

    public String getEtternavn( )
    {

        return etternavn;
    }

    public void setEtternavn( String etternavn )
    {

        this.etternavn = etternavn;
    }

    public String getTelefon( )
    {

        return telefon;
    }

    public void setTelefon( String telefon )
    {

        this.telefon = telefon;
    }


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        EditText studentFornavn = (EditText) findViewById(R.id.fornavn);
        EditText studentEtternavn = (EditText) findViewById(R.id.etternavn);
        EditText studentTelefon = (EditText) findViewById(R.id.telefon);

        utskrift = (TextView) findViewById(R.id.utskrift);
    }
}
