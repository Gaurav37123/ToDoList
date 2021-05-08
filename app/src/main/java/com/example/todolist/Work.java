package com.example.todolist;

import java.util.Date;

public class Work
{
    String date;
    String time;
    String work;

    public Work() {
    }

    public Work(String date, String time, String work) {
        this.date = date;
        this.time = time;
        this.work = work;
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

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
