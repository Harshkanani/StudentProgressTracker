package com.spt.studentprogresstracker;

public class Attandance {

    public String date;
    public String status;
    public String type;

    public Attandance(String date, String status,String type) {
        this.date = date;
        this.status = status;
        this.type=type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
