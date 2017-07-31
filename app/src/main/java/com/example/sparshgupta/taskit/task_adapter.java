package com.example.sparshgupta.taskit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by sparshgupta on 14/02/17.
 */

public class task_adapter extends ArrayAdapter {

    Context mContext;
    ArrayList<TaskDetailsClass> mTask;

    public task_adapter(Context context, ArrayList<TaskDetailsClass> objects) {
        super(context, 0, objects);
        mContext = context;
        mTask = objects;
    }

    static class TaskHolder{
        TextView title, day, time, noteTextView, isAlarmTextView;
        TaskHolder(TextView title, TextView day, TextView time, TextView noteTextView, TextView isAlarmTextView){
            this.title = title;
            this.day = day;
            this.time = time;
            this.noteTextView = noteTextView;
            this.isAlarmTextView = isAlarmTextView;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.adapter_view, null);
            TextView title = (TextView) convertView.findViewById(R.id.titleSpace);   //since finding view is tedious and consumes more resources.. hence we use holder class
            TextView day = (TextView) convertView.findViewById(R.id.dateArea);
            TextView time = (TextView) convertView.findViewById(R.id.timeArea);
            TextView notes = (TextView) convertView.findViewById(R.id.descriptionTextView);
            TextView isAlarm = (TextView) convertView.findViewById(R.id.reminderShowTextView);
            TaskHolder holder = new TaskHolder(title, day, time, notes, isAlarm);
            convertView.setTag(holder);
        }
        TaskHolder holder = (TaskHolder) convertView.getTag();
        TaskDetailsClass t = mTask.get(position);
        holder.title.setText(t.task);
        holder.day.setText(t.dateOfTask);
        holder.time.setText(t.timeOfTask);
        if(t.notes.length() == 0){
            holder.noteTextView.setText("No description");
        }else{
            holder.noteTextView.setText(t.notes);
        }
        if(t.isAlarm == 0){
            holder.isAlarmTextView.setText("ALARM OFF");
        }
        else{
            holder.isAlarmTextView.setText("ALARM ON");
        }
        return convertView;
    }
}
