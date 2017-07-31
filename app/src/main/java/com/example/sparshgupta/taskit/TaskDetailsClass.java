package com.example.sparshgupta.taskit;

/**
 * Created by sparshgupta on 14/02/17.
 */

public class TaskDetailsClass {


    String task;
    String dateOfTask;
    String timeOfTask;
    int id;
    String notes;
    int isAlarm;

    TaskDetailsClass(int id, String task, String dateOfTask, String timeOfTask, String notes, int isAlarm){
        this.task = task;
        this.dateOfTask = dateOfTask;
        this.timeOfTask = timeOfTask;
        this.notes = notes;
        this.id = id;
        this.isAlarm = isAlarm;
    }
}