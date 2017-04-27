package com.mrede003.takehome.farmlogs.farmnotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Home extends AppCompatActivity {

    private static final String TAG = "Home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //if Location Permission is not granted, ask for it
        if(!Helper.hasLocationPermission(this))
        {
            Helper.requestLocationPermission(this);
        }
        DatabaseHelper.getInstance(this);
    }
    public void openNewNotes(View view)
    {
        Intent intent = new Intent(this, NoteDisplay.class);
        startActivity(intent);
    }
}


