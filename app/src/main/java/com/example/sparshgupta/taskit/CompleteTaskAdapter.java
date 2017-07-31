package com.example.sparshgupta.taskit;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sparshgupta on 13/03/17.
 */

public class CompleteTaskAdapter extends RecyclerView.Adapter<CompleteTaskAdapter.TaskHolder>{

    Context mContext;
    ArrayList<CompleteTask1> mObjects;

    public CompleteTaskAdapter(@NonNull Context context, @NonNull ArrayList<CompleteTask1> objects) {
        mContext = context;
        mObjects = objects;
    }


    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_view, null);
        TaskHolder taskHolder = new TaskHolder(v);
        return taskHolder;
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position){
        CompleteTask1 completeTask = mObjects.get(position);
        holder.titleTextView.setText(completeTask.title);
        holder.dateTextView.setText(completeTask.date);
        holder.timeTextView.setText(completeTask.time);
        holder.notesTextView.setText(completeTask.notes);
    }


    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder{

        TextView titleTextView, dateTextView, timeTextView, notesTextView;

        public TaskHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleSpace);
            dateTextView = (TextView) itemView.findViewById(R.id.dateArea);
            timeTextView = (TextView) itemView.findViewById(R.id.timeArea);
            notesTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
