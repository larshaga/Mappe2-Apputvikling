
package com.example.larsh.mappe2s305357;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListViewActivity extends AppCompatActivity
{

    DBHandler db;
    StableArrayAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        db = new DBHandler(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);

        FloatingActionButton btn_addStudent = (FloatingActionButton) findViewById(R.id.btn_addStudent);
        FloatingActionButton btn_newMsg = (FloatingActionButton) findViewById(R.id.btn_sendNewMsg);

        btn_newMsg.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick( View view )
            {

                Intent sendNewMessage = new Intent(ListViewActivity.this,NewMessageActivity.class);
                startActivity(sendNewMessage);
            }
        });

        btn_addStudent.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick( View view )
            {

                Intent addNewStudent = new Intent(ListViewActivity.this, NewStudentActivity.class);
                startActivity(addNewStudent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listview_menu, menu);
        return true;
    }

    @Override
    public void onResume( )
    {

        super.onResume();
        final ListView listAllStudents = (ListView) findViewById(R.id.studentlistview);
        listAllStudents.invalidateViews();

        final List<Student> studenter = db.findAllStudents();
        final ArrayList<String> allStudentsInList = new ArrayList<>(studenter.size());

        int i = 0;
        for (Student studentInfo : studenter)
        {
            allStudentsInList.add(i, studentInfo.getLastName() + ", " + studentInfo.getFirstname());
            Log.i("listing all studentens", studentInfo.getLastName() + ", " + studentInfo.getFirstname());
            i++;
        }

        adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, allStudentsInList);
        listAllStudents.setAdapter(adapter);


        listAllStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick( AdapterView<?> adapterView, View view, final int i, long l )
            {

                new AlertDialog.Builder(ListViewActivity.this)
                        .setMessage("Are you sure you want to delete " + allStudentsInList.get(i) + " ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {

                            @Override
                            public void onClick( DialogInterface dialog, int which )
                            {

                                String[] slettStudent = allStudentsInList.get(i).split(",");
                                String slettStudentEtternavn = slettStudent[0].trim();
                                String slettStudentFornavn = slettStudent[1].trim();

                                db.slettKontaktString(slettStudentFornavn, slettStudentEtternavn);
                                recreate();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });


        listAllStudents.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int i, long l )
            {

                Log.i("ItemClick", allStudentsInList.get(i));
                Intent seeDetailedInfoAboutStudent = new Intent(ListViewActivity.this, DetailedActivity.class);
                seeDetailedInfoAboutStudent.putExtra("StudentFirstAndLastName", allStudentsInList.get(i));
                startActivity(seeDetailedInfoAboutStudent);

            }

        });

    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {

        if (item.getItemId() == R.id.preferences)
        {
            Intent changePreferences = new Intent(this, SettingsActivity.class);
            startActivity(changePreferences);
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
