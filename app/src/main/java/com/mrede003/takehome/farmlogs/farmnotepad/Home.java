package com.mrede003.takehome.farmlogs.farmnotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private static ArrayList<Note> noteList;
    private ListView notesListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //if Location Permission is not granted, ask for it
        if(!Helper.hasLocationPermission(this))
        {
            Helper.requestLocationPermission(this);
        }
        notesListView = (ListView) findViewById(R.id.notesListView);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(Home.this, NoteDisplay.class);
                intent.putExtra(getString(R.string.note_id), noteList.get(position).getId());
                intent.putExtra(getString(R.string.title), noteList.get(position).getTitle());
                intent.putExtra(getString(R.string.date), noteList.get(position).getDate());
                intent.putExtra(getString(R.string.latitude), noteList.get(position).getLatitude());
                intent.putExtra(getString(R.string.longitude), noteList.get(position).getLongitude());
                intent.putExtra(getString(R.string.content), noteList.get(position).getContent());
                intent.putExtra(getString(R.string.pic1), noteList.get(position).getPic1());
                intent.putExtra(getString(R.string.pic2), noteList.get(position).getPic2());
                intent.putExtra(getString(R.string.pic3), noteList.get(position).getPic3());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        noteList=DatabaseHelper.getInstance(this).getAllNotes();
        CustomListAdapter listAdapter=new CustomListAdapter(this,noteList);
        notesListView.setAdapter(listAdapter);
    }

    public void openNewNotes(View view)
    {
        Intent intent = new Intent(this, NoteDisplay.class);
        startActivity(intent);
    }
}


