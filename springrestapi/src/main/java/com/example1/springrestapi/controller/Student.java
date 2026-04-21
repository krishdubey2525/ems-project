package com.example1.springrestapi.controller;


public class Student {
    private int id;
    private String fname;
    private String lname;

    public Student(int id, String fname, String lname) {
        this.lname = lname;
        this.fname = fname;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
