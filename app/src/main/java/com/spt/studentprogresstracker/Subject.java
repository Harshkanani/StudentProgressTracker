package com.spt.studentprogresstracker;

public class Subject {
    public String subname;
    public String subcode;

    public Subject(String code, String name) {
        this.subname = name;
        this.subcode = code;
    }

    public String getSubname() {
        return subname;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubname(String name) {
        this.subname = name;
    }

    public void setSubcode(String code) {
        this.subcode = code;
    }
}
