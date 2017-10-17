package com.example.larsh.mappe2s305357;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    DBHandler db;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        db = new DBHandler(this);

        TextView studentFirstName = (TextView) findViewById(R.id.studentfirstname);
        TextView studentLastName = (TextView) findViewById(R.id.studentlastname);
        TextView studentID = (TextView) findViewById(R.id.studentid);
        TextView studentPhonenumber = (TextView) findViewById(R.id.studentphonenumber);


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


    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {

        switch (item.getItemId())
        {
            case R.id.deletestudent:
                db.slettKontaktString(detailsAboutStudentFirstName, detailsAboutStudentLastName);
                Toast.makeText(DetailedActivity.this, "Student deleted", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.editstudent:
                Intent editStudent = new Intent(this, EditStudentActivity.class);
                Log.i("test", detailedStudentName);
                editStudent.putExtra("StudentFirstName", detailsAboutStudentFirstName);
                editStudent.putExtra("StudentLastName", detailsAboutStudentLastName);
                editStudent.putExtra("StudentPhonenumber", detailedStudentPhonenumber);
                startActivity(editStudent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


}
