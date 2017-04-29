package com.mrede003.takehome.farmlogs.farmnotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by mrede003 on 4/25/17.
 * Database Helper class to do all interfacing between java code and SQLite database
 * For the purpose of re-use  I would normally put in an update method for each member of the note
 * class. IE: updateContent, updateTitle, updateDate, etc.... but because this is just an assessment
 * and my time is a bit limited I only put in what is necessary
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notesDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME="notes";

    private static final String NOTE_ID = "id";
    private static final String TITLE = "title";
    private static final String DATE= "date";
    private static final String CONTENTS = "contents";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String PIC1_PATH= "pic_one";
    private static final String PIC2_PATH= "pic_two";
    private static final String PIC3_PATH= "pic_three";

    private static DatabaseHelper sInstance;

    //Private Constructor to adhere to singleton design pattern practice
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Singleton design to ensure only one copy of this database helper class is used
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
                TITLE + " TEXT," +
                DATE + " TEXT," +
                LATITUDE +" DOUBLE,"+
                LONGITUDE +" DOUBLE,"+
                CONTENTS + " TEXT," +
                PIC1_PATH + " TEXT," +
                PIC2_PATH + " TEXT," +
                PIC3_PATH + " TEXT" +
                ")";

        db.execSQL(CREATE_NOTES_TABLE);
    }
    //Overidden method that is called when the database version is incremented
    //Normally if there was a database on a server I was pulling from
    //I would just drop the table and re-populate. But this is only an assessment
    //so I'll just drop and recreate as if that was happening
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
    //Method to add new note (row) to the database
    public void addNote(Note note)
    {
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(DATE, note.getDate());
        values.put(LATITUDE, note.getLatitude());
        values.put(LONGITUDE, note.getLongitude());
        values.put(CONTENTS, note.getTitle());
        values.put(PIC1_PATH, note.getPic1());
        values.put(PIC2_PATH, note.getPic2());
        values.put(PIC3_PATH, note.getPic3());
        database.insert(TABLE_NAME, null, values);
        database.close();
    }
    //Method to update the content of a note. Returns n>0 if a note was updated
    //Returns 0 if the note did not exist
    public int updateNote(Note note)
    {
        SQLiteDatabase database= this.getWritableDatabase();
        int output=0;
        ContentValues values= new ContentValues();
        values.put(CONTENTS, note.getContent());
        values.put(PIC1_PATH, note.getPic1());
        values.put(PIC2_PATH, note.getPic2());
        values.put(PIC3_PATH, note.getPic3());
        output=database.update(TABLE_NAME, values, NOTE_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
        database.close();
        return output;
    }

    //Method to delete a note. Returns n>0 if a note(row) was updated
    //Returns 0 if the note did not exist
    public int deleteNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int output=0;
        output=db.delete(TABLE_NAME, NOTE_ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
        db.close();
        return output;
    }
    //Method that returns a list of all the notes held within the database
    //
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note=new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDate(cursor.getString(2));
                note.setLatitude(Double.parseDouble(cursor.getString(3)));
                note.setLongitude(Double.parseDouble(cursor.getString(4)));
                note.setContent(cursor.getString(5));
                note.setPic1(cursor.getString(6));
                note.setPic2(cursor.getString(7));
                note.setPic3(cursor.getString(8));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return notes;
    }
    //Method to delete all ro
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }
}
