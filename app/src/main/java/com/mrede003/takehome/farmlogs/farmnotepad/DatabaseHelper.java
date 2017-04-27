package com.mrede003.takehome.farmlogs.farmnotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mrede003 on 4/25/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notesDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME="notes";

    private static final String NOTE_ID = "id";
    private static final String TITLE = "title";
    private static final String DATE= "date";
    private static final String TIME = "time";
    private static final String CONTENTS = "contents";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String PIC1_PATH= "pic_one";
    private static final String PIC2_PATH= "pic_two";
    private static final String PIC3_PATH= "pic_three";

    private static DatabaseHelper sInstance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                NOTE_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                TITLE + " TEXT" +
                DATE + " TEXT" +
                TIME + " TEXT" +
                LATITUDE +" DOUBLE"+
                LONGITUDE +" DOUBLE"+
                CONTENTS + " TEXT" +
                PIC1_PATH + " TEXT" +
                PIC2_PATH + " TEXT" +
                PIC3_PATH + " TEXT" +
                ")";

        db.execSQL(CREATE_NOTES_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);                 //Normally if there was a database on a server I was pulling from
            onCreate(db);                                                     //I would just drop the table and re-populate. But this is only an assessment
                                                                              //so I'll just drop and recreate as if that was happening
        }
    }
    //Method to add new note (row) to the database
    public void addNote(Note note)
    {
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(DATE, note.getDate());
        values.put(TIME, note.getTime());
        values.put(LATITUDE, note.getLatitude());
        values.put(LONGITUDE, note.getLongitude());
        values.put(CONTENTS, note.getTitle());
        values.put(TITLE, note.getTitle());
    }

}
