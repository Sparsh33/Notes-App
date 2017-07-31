package com.example.sparshgupta.taskit;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CompletedTaskActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CompleteTask1> list;
    CompleteTaskAdapter completeTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task);
        recyclerView = (RecyclerView) findViewById(R.id.completedTaskListView);
        list = new ArrayList<>();
        completeTaskAdapter = new CompleteTaskAdapter(this, list);
        recyclerView.setAdapter(completeTaskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("Completed Tasks");
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view,final int position) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(CompletedTaskActivity.this);
                        View v = getLayoutInflater().inflate(R.layout.complete_task_dialog, null);
                        dialog.setView(v);
                        final AlertDialog d = dialog.create();
                        d.show();
                        TextView restoreTextView = (TextView) v.findViewById(R.id.restoreTextView);
                        restoreTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d.dismiss();
                                CompleteTask1 task = list.get(position);
                                String title = task.title;
                                String date = task.date;
                                String time = task.time;
                                String notes = task.notes;
                                TaskHelper taskHelper = new TaskHelper(CompletedTaskActivity.this);
                                SQLiteDatabase db = taskHelper.getWritableDatabase();
                                ContentValues cv = new ContentValues();
                                cv.put(TaskHelper.TASK_TITLE, title);
                                cv.put(TaskHelper.TASK_DATE, date);
                                cv.put(TaskHelper.TASK_TIME, time);
                                cv.put(TaskHelper.TASK_DESCRIPTION, notes);
                                cv.put(TaskHelper.IS_ALARM, 0);
                                db.insert(TaskHelper.TASK_TABLE_NAME, null, cv);
                                db.close();
                                long id = task.getId();
                                CompleteTask1.delete(CompleteTask1.class, id);
                                setUpViews();
                            }
                        });
                        TextView deleteTextView_CompletedTask = (TextView) v.findViewById(R.id.deleteTextView_CompletedTask);
                        deleteTextView_CompletedTask.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                d.dismiss();
                                AlertDialog.Builder dialog = new AlertDialog.Builder(CompletedTaskActivity.this);
                                dialog.setTitle("Confirm");
                                dialog.setMessage("Are you sure you want to delete this task? Task will be deleted permanently");
                                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setUpViews();
                                    }
                                });
                                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                dialog.create().show();
                            }
                        });

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        setUpViews();
    }

    private void setUpViews() {
        list.clear();
        List<CompleteTask1> tempList = new Select().from(CompleteTask1.class).execute();
        list.addAll(tempList);
        completeTaskAdapter.notifyDataSetChanged();
    }

}
