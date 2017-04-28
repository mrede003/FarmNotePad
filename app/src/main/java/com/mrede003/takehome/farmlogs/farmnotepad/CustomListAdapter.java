package com.mrede003.takehome.farmlogs.farmnotepad;

import android.app.Activity;
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
        dateTime.setText("Created on "+noteList.get(position).getDate()+
                " at "+noteList.get(position).getTime());
        return vi;
    }
}
