package com.mrede003.takehome.farmlogs.farmnotepad;

/**
 * Created by mrede003 on 4/26/17.
 * Object to represent a note.
 */

public class Note {
    private String title;
    private String content;
    private String date;
    private String time;
    private String pic1;
    private String pic2;
    private String pic3;
    private double latitude;
    private double longitude;

    public Note(String title, String content, String date, String time, String pic1, String pic2, String pic3, double latitude, double longitude)
    {
        this.title=title;
        this.content=content;
        this.date=date;
        this.time=time;
        this.pic1=pic1;
        this.pic2=pic2;
        this.pic3=pic3;
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public Note()
    {
        this.title="";
        this.content="";
        this.date="";
        this.time="";
        this.pic1="";
        this.pic2="";
        this.pic3="";
        this.latitude=0.0;
        this.longitude=0.0;
    }
    public Note(String title, String content, String date, String time)
    {
        this.title=title;
        this.content=content;
        this.date=date;
        this.time=time;
        this.pic1="";
        this.pic2="";
        this.pic3="";
        this.latitude=0.0;
        this.longitude=0.0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
