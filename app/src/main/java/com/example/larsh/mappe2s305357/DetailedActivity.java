package com.example.larsh.mappe2s305357;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity
{

    String detailedStudentName = null;
    String detailsAboutStudentFirstName = null;
    String detailsAboutStudentLastName = null;
    String detailedStudentID = null;
    String detailedStudentPhonenumber = null;
    EditText studentFirstName;
    EditText studentLastName;
    EditText studentPhonenumber;
    MenuItem editMenuIcon;
    MenuItem doneMenuIcon;
    MenuItem deleteMenuIcon;
    DBHandler db;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        db = new DBHandler(this);


        studentFirstName = (EditText) findViewById(R.id.editfirstname);
        studentLastName = (EditText) findViewById(R.id.editlastname);
        TextView studentID = (TextView) findViewById(R.id.studentid);
        studentPhonenumber = (EditText) findViewById(R.id.editphonenumber);


        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);

        Intent nameFromMain = getIntent();
        Bundle bundle = nameFromMain.getExtras();
        detailedStudentName = bundle.getString("StudentFirstAndLastName");

        Log.i("detailedStudentName", detailedStudentName);

        String[] detailsAboutStudent = detailedStudentName.split(",");
        detailsAboutStudentLastName = detailsAboutStudent[0].trim();
        detailsAboutStudentFirstName = detailsAboutStudent[1].trim();

        Log.i("det", detailsAboutStudentFirstName);
        Log.i("det", detailsAboutStudentLastName);
        final List<Student> studenter = db.findAStudent(detailsAboutStudentFirstName, detailsAboutStudentLastName);

        for (Student studentInfo : studenter)
        {
            detailedStudentID = String.valueOf(studentInfo.get_ID());
            detailedStudentPhonenumber = studentInfo.getPhonenumber();
        }


        studentFirstName.setText(detailsAboutStudentFirstName);
        studentLastName.setText(detailsAboutStudentLastName);
        studentID.setText(detailedStudentID);
        studentPhonenumber.setText(detailedStudentPhonenumber);

        // Enables the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        editMenuIcon = menu.findItem(R.id.editstudent);
        doneMenuIcon = menu.findItem(R.id.editstudentdone);
        deleteMenuIcon = menu.findItem(R.id.deletestudent);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {

        switch (item.getItemId())
        {
            case R.id.deletestudent:
                db.slettKontaktString(detailsAboutStudentFirstName, detailsAboutStudentLastName);
                Toast.makeText(DetailedActivity.this, "You deleted: " + detailedStudentName, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.editstudent:

                studentFirstName.setEnabled(true);
                studentLastName.setEnabled(true);
                studentPhonenumber.setEnabled(true);


                editMenuIcon.setVisible(false);
                deleteMenuIcon.setVisible(false);
                doneMenuIcon.setVisible(true);

                Toast.makeText(DetailedActivity.this, "You can now edit the student", Toast.LENGTH_LONG).show();
                break;
            case R.id.editstudentdone:
                oppdater();

                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void oppdater( )
    {

        Student kontakt = new Student();
        kontakt.setFirstname(studentFirstName.getText().toString());
        kontakt.setLastName(studentLastName.getText().toString());
        kontakt.setPhonenumber(studentPhonenumber.getText().toString());
        kontakt.set_ID(Long.parseLong(detailedStudentID));
        db.oppdaterKontakt(kontakt);
    }

}
