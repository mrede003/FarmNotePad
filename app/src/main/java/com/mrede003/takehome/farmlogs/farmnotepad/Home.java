package com.mrede003.takehome.farmlogs.farmnotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

public class Home extends AppCompatActivity {

    private static final String TAG = "Home";
    private ListView notesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //if Location Permission is not granted, ask for it
        if(!Helper.hasLocationPermission(this))
        {
            Helper.requestLocationPermission(this);
        }
        DatabaseHelper.getInstance(this).deleteAll();
        Note n=new Note("Corn and Maze", "the content", "04/29/2017", "12:15 PM");
        Note y=new Note("Corn and Ham", "the content", "04/29/2017", "12:15 PM");
        Note h=new Note("Corn and Chicken", "the content", "04/29/2017", "12:15 PM");
        Note g=new Note("Corn and Beef", "the content", "04/29/2017", "12:15 PM");
        Note d=new Note("Corn and Steak", "the content", "04/29/2017", "12:15 PM");

        DatabaseHelper.getInstance(this).addNote(n);
        DatabaseHelper.getInstance(this).addNote(y);
        DatabaseHelper.getInstance(this).addNote(h);
        DatabaseHelper.getInstance(this).addNote(g);
        DatabaseHelper.getInstance(this).addNote(d);

        notesList = (ListView) findViewById(R.id.notesListView);
        CustomListAdapter listAdapter=new CustomListAdapter(this,DatabaseHelper.getInstance(this).getAllNotes());
        notesList.setAdapter(listAdapter);
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(Home.this, NoteDisplay.class);
                startActivity(intent);
            }
        });
    }
    public void openNewNotes(View view)
    {
        Intent intent = new Intent(this, NoteDisplay.class);
        startActivity(intent);
    }
}


