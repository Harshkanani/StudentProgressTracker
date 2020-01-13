package com.spt.studentprogresstracker;

public class Result {

    public String date;
    public String type;
    public String marks;
    public String status;

    public Result(String date, String type,String marks,String status) {
        this.date = date;
        this.type = type;
        this.marks = marks;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarks() {
        return marks;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
