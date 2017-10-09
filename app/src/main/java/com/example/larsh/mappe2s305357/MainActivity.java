package com.example.larsh.mappe2s305357;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.larsh.mappe2s305357.R.id.all;
import static com.example.larsh.mappe2s305357.R.id.center;
import static com.example.larsh.mappe2s305357.R.id.studentlistview;
import static com.example.larsh.mappe2s305357.R.id.utskrift;

public class MainActivity extends AppCompatActivity
{

    DBHandler db;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {

        db = new DBHandler(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listAllStudents = (ListView) findViewById(R.id.studentlistview);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);

        FloatingActionButton btn_addStudent = (FloatingActionButton) findViewById(R.id.btn_addStudent);

        btn_addStudent.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick( View view )
            {

                Intent addNewStudent = new Intent(MainActivity.this, NewStudentActivity.class);
                startActivity(addNewStudent);
            }
        });


        final List<Student> studenter = db.finnAlleKontakter();
        final ArrayList<String> list = new ArrayList<>();

        int i = 0;
        for (Student kontakt : studenter)
        {

            list.add(i, kontakt.getFirstname() + ", " + kontakt.getLastName());
            i++;
        }


        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listAllStudents.setAdapter(adapter);

        listAllStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick( AdapterView<?> adapterView, View view, int i, long l )
            {
                Log.i("Long clicked","pos: " + i + " long: " + l);
                Log.i("Long clicked4", list.get(i));

                PopupMenu popup = new PopupMenu(MainActivity.this,view);
                popup.getMenuInflater().inflate(R.menu.poupup_menu,popup.getMenu());

                popup.show();
                return false;
            }
        });

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
            case R.id.refresh:
                recreate();
                Toast.makeText(this, "ListView refreshed", Toast.LENGTH_SHORT).show();
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
                Intent sendNewMessage = new Intent(this, NewMessageActivity.class);
                startActivity(sendNewMessage);
                break;
            case R.id.preferences:
                Intent changePreferences = new Intent(this, SettingsActivity.class);
                startActivity(changePreferences);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
