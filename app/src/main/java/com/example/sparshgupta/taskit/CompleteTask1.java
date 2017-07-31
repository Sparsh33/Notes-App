package com.example.sparshgupta.taskit;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sparshgupta on 13/03/17.
 */
@Table(name = "CompletedTaskTable")
public class CompleteTask1 extends Model{

    @Column(name = "title")
    String title;
    @Column(name = "time")
    String time;
    @Column(name = "date")
    String date;
    @Column(name = "notes")
    String notes;

    public CompleteTask1(){
        super();
    }

    public CompleteTask1(String title, String time, String date, String notes){
        super();
        this.title = title;
        this.time = time;
        this.date = date;
        this.notes = notes;
    }
}
