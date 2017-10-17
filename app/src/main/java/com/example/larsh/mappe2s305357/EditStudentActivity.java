package com.example.larsh.mappe2s305357;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EditStudentActivity extends AppCompatActivity
{
    String firstName = null;
    String lastName = null;
    String phonenumber = null;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);

        TextView studentFirstName = (TextView) findViewById(R.id.editfirstname);
        TextView studentLastName = (TextView) findViewById(R.id.editlastname);
        TextView studentPhonenumber = (TextView) findViewById(R.id.editphonenumber);

        Intent studentFromDetail = getIntent();
        Bundle bundle = studentFromDetail.getExtras();
        firstName = bundle.getString("StudentFirstName");
        lastName = bundle.getString("StudentLastName");
        phonenumber = bundle.getString("StudentPhonenumber");

        studentFirstName.setText(firstName);
        studentLastName.setText(lastName);
        studentPhonenumber.setText(phonenumber);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {

        switch (item.getItemId())
        {
            case R.id.done:



                Toast.makeText(this, "Student saved", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
/
    public void oppdater(View v ) {
        Kontakt kontakt = new Kontakt();
        kontakt.setNavn(navninn.getText().toString());
        kontakt.setTelefon(telefoninn.getText().toString());
        kontakt.set_ID(Long.parseLong(idinn.getText().toString()));
        db.oppdaterKontakt(kontakt);
    }*/
}
