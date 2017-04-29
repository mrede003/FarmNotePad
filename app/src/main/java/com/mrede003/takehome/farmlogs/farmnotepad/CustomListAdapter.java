package com.mrede003.takehome.farmlogs.farmnotepad;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mrede003 on 4/27/17.
 */

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater=null;
    private ArrayList<Note> noteList;
    private final String create="Created on ";

    public CustomListAdapter(Activity a, ArrayList<Note> noteList) {
        this.noteList=noteList;
        activity = a;
        inflater = LayoutInflater.from(activity);
    }

    public int getCount() {
        return noteList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        if(convertView==null)                               //null check to increase performance so a new view object isn't instantiated every time
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView dateTime = (TextView)vi.findViewById(R.id.date_time); // notes date and time
        //ImageView thumbImage=(ImageView)vi.findViewById(R.id.note_image); // note image

        title.setText(noteList.get(position).getTitle());
        dateTime.setText(create+noteList.get(position).getDate());        //Can't access R.string from a class without context without doing some weird hacky stuff
                                                                          //IE: making singleton class in home that returns context or just passing a parameter
                                                                          //Would love to hear a seniors devs opinion on how to solve accessing R.string from a pure java class.
        return vi;
    }
}
