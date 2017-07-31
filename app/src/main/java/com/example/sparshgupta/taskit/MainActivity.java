package com.example.sparshgupta.taskit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import static com.example.sparshgupta.taskit.TaskHelper.TASK_TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    ArrayList<TaskDetailsClass> list;
    task_adapter adapter;
    TaskHelper taskHelper;
    CompleteTask1 completeTask;
    public final static String POSITION = "position";
    public static final String SHARED_PREFERENCE_FIRST_LOGIN = "TaskIiSharedPreference";
    public static final String FIRST_LOGIN = "firstLogin";
    SharedPreferences firstLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Pending Tasks");
        list = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.mainList);
        firstLogin = getSharedPreferences(SHARED_PREFERENCE_FIRST_LOGIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = firstLogin.edit();
        Boolean login = firstLogin.getBoolean(FIRST_LOGIN, true);
        if(login){
            View emptyView = getLayoutInflater().inflate(R.layout.blankmessagedisplay, null);
            addContentView(emptyView, listView.getLayoutParams()); // You're using the same params as listView
            listView.setEmptyView(emptyView);
            editor.putBoolean(FIRST_LOGIN, false);
        }else{
            View emptyView = getLayoutInflater().inflate(R.layout.blank_list_layout_after_firstlogin, null);
            addContentView(emptyView, listView.getLayoutParams()); // You're using the same params as listView
            listView.setEmptyView(emptyView);
        }
        adapter = new task_adapter(this, list);
        listView.setAdapter(adapter);
        taskHelper = new TaskHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, Main2Activity.class);
                startActivityForResult(i, 1);
            }
        });
        setUpViews();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                View v = getLayoutInflater().inflate(R.layout.listitemclickdialog_view, null);
                dialog.setView(v);
                final AlertDialog d = dialog.create();
                d.show();
                TextView edit = (TextView) v.findViewById(R.id.edit);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        Intent i = new Intent();
                        i.putExtra("id", list.get(position).id);
                        i.setClass(MainActivity.this, Main2Activity.class);
                        startActivityForResult(i, 1);
                    }
                });
                TextView delete = (TextView) v.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        AlertDialog.Builder d1 = new AlertDialog.Builder(MainActivity.this);
                        d1.setTitle("Confirm");
                        d1.setMessage("Delete task ? ");
                        d1.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = taskHelper.getWritableDatabase();
                                db.delete(TaskHelper.TASK_TABLE_NAME, "_id = " + list.get(position).id, null);
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                                db.close();
                            }
                        });
                        d1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        d1.create().show();
                    }
                });
                TextView markAsDone = (TextView) v.findViewById(R.id.markAsDone);
                markAsDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        String title = list.get(position).task;
                        String date = list.get(position).dateOfTask;
                        String time = list.get(position).timeOfTask;
                        String notes = list.get(position).notes;
                        completeTask = new CompleteTask1(title, time, date, notes);
                        completeTask.save();
                        SQLiteDatabase db = taskHelper.getWritableDatabase();
                        db.delete(taskHelper.TASK_TABLE_NAME, "_id = " + list.get(position).id, null);
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        db.close();
                    }
                });

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                View v = getLayoutInflater().inflate(R.layout.delete_dialog, null);
                dialog.setView(v);
                final AlertDialog d = dialog.create();
                dialog.show();
                TextView deleteText = (TextView) v.findViewById(R.id.deletedialogTextView);
                deleteText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                        AlertDialog.Builder d1 = new AlertDialog.Builder(MainActivity.this);
                        d1.setTitle("Confirm");
                        d1.setMessage("Delete task ? ");
                        d1.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = taskHelper.getWritableDatabase();
                                db.delete(taskHelper.TASK_TABLE_NAME, "_id = " + list.get(position).id, null);
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                                db.close();
                            }
                        });
                        d1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        d1.create().show();
                    }
                });
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.completedTask){
            Intent i = new Intent();
            i.setClass(MainActivity.this, CompletedTaskActivity.class);
            startActivity(i);
        }
        if(id == R.id.feedback){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("mailto:gupta.sparsh33@gmail.com"));
            i.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            startActivity(i);
        }
        if(id == R.id.contactUs){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:8447690202"));
            startActivity(i);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                setUpViews();
            }
        }
    }

    private void setUpViews() {
        SQLiteDatabase db = taskHelper.getReadableDatabase();
        list.clear();
        Cursor c = db.query(TaskHelper.TASK_TABLE_NAME, null, null, null, null, null, null);
        while(c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(TaskHelper.TASK_COLUMN_ID));
            String title = c.getString(c.getColumnIndex(TaskHelper.TASK_TITLE));
            String taskDate = c.getString(c.getColumnIndex(TaskHelper.TASK_DATE));
            String taskTime = c.getString(c.getColumnIndex(TaskHelper.TASK_TIME));
            String notes = c.getString(c.getColumnIndex(TaskHelper.TASK_DESCRIPTION));
            if(notes == null){
                notes = "None";
            }
            int isAlarm = c.getInt(c.getColumnIndex(TaskHelper.IS_ALARM));
            TaskDetailsClass taskDetailsClass = new TaskDetailsClass(id, title, taskDate, taskTime, notes, isAlarm);
            list.add(taskDetailsClass);
        }
        adapter.notifyDataSetChanged();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpViews();
    }
}
