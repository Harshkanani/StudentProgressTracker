package com.spt.studentprogresstracker;

public class Notice {

    public String title;
    public String date;
    public String name;

    public Notice(String title, String date,String name) {
        this.name = name;
        this.title = title;
        this.date=date;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
