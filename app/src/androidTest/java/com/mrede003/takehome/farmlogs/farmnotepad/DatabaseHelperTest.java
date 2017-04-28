package com.mrede003.takehome.farmlogs.farmnotepad;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Warning** Running these tests will permanently clear the current database
 * These tests also run under the assumption that the deleteAll function in database helper is working
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseHelperTest {
    private Context context;
    private DatabaseHelper db;
    @Before
    public void init() {
        context=InstrumentationRegistry.getTargetContext();
        db=DatabaseHelper.getInstance(context);
    }

    //Test to assert the add function and getAllNotes function is working properly
    //Asserts the database has no values in it.
    @Test
    public void AddNoteTest() throws Exception {
        db.deleteAll();
        assertEquals(0,db.getAllNotes().size());
        Note note=new Note("Test Note 1", "This is the contents of a sample note", "4/27/2017", "5:00PM");
        db.addNote(note);
        assertEquals(1, db.getAllNotes().size());
    }
    //Test to assert the delete function in the databasehelper class is functioning correctly
    //Asserts the database has one value in it.
    @Test
    public void deleteNoteTest() throws Exception {
        assertEquals(1, db.getAllNotes().size());
        Note note=db.getAllNotes().get(0);
        db.deleteNote(note);
        assertEquals(0,db.getAllNotes().size());
    }
}