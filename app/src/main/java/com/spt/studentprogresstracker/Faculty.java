package com.spt.studentprogresstracker;

public class Faculty {

    public String fname;
    public String femail;
    public String fsub;

    public Faculty(String fname, String femail,String fsub) {
        this.fname = fname;
        this.femail = femail;
        this.fsub = fsub;
    }

    public String getFsub() {
        return fsub;
    }

    public String getFname() {
        return fname;
    }

    public void setFsub(String fsub) {
        this.fsub = fsub;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFemail(String femail) {
        this.femail = femail;
    }

    public String getFemail() {
        return femail;
    }
}
