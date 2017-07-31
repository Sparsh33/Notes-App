package com.example.sparshgupta.taskit;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import static com.example.sparshgupta.taskit.MainActivity.BOOL;
//import static com.example.sparshgupta.taskit.MainActivity.FROM;
import static com.example.sparshgupta.taskit.MainActivity.POSITION;


public class Main2Activity extends AppCompatActivity {

    int id;
    TextView eDate, eTime;
    EditText eTaskName, eTaskNotes;
    String finalMonth, finalAMPM, stringDate, stringTime;
    Switch reminderSwitch;
    String alarmTime;
    String alarmDate;
    String stringEpocTime;
    long epoch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Calendar d = Calendar.getInstance();
        final int sYear = d.get(Calendar.YEAR);
        final int sMonth = d.get(Calendar.MONTH);
        final int sDate = d.get(Calendar.DATE);
        final int hours = d.get(Calendar.HOUR_OF_DAY);
        final int minutes = d.get(Calendar.MINUTE);
        Intent i = getIntent();
        reminderSwitch = (Switch) findViewById(R.id.switchReminder);
        eTaskNotes = (EditText) findViewById(R.id.notesEditText) ;
        TextView time = (TextView) findViewById(R.id.taskTime);
        final TextView date = (TextView) findViewById(R.id.taskDate);
        id = i.getIntExtra("id", -1);

        if(id != -1){
            populateViews();
            getSupportActionBar().setTitle("Edit Task");
        }else{
            getSupportActionBar().setTitle("New Task");
        }
        if(minutes % 10 == minutes)
            alarmTime = hours + ":0" + minutes + ":00";
        else
            alarmTime = hours + ":" + minutes + ":00";
        String s = "PM";
        if (hours % 12 == hours) {
            s = "AM";
        }
        int h = hours;
        if (h != 12)
            h = h % 12;
        if (h == 0) {
            h = 12;
        }
        if(id == -1) {
            if (minutes % 10 == minutes) {
                stringTime = "" + h + ":0" + minutes + " " + s;
                time.setText("" + h + ":0" + minutes + " " + s);
            } else {
                stringTime = "" + h + ":" + minutes + " " + s;
                time.setText("" + h + ":" + minutes + " " + s);
            }
        }
        String m = "Jan";
        if (sMonth == 0) {
            m = "Jan";
        } else if (sMonth == 1) {
            m = "Feb";
        } else if (sMonth == 2) {
            m = "Mar";
        } else if (sMonth == 3) {
            m = "Apr";
        } else if (sMonth == 4) {
            m = "May";
        } else if (sMonth == 5) {
            m = "Jun";
        } else if (sMonth == 6) {
            m = "Jul";
        } else if (sMonth == 7) {
            m = "Aug";
        } else if (sMonth == 8) {
            m = "Sep";
        } else if (sMonth == 9) {
            m = "Oct";
        } else if (sMonth == 10) {
            m = "Nov";
        } else if (sMonth == 11) {
            m = "Dec";
        }
        if(id == -1)
            date.setText("" + sDate + " " + m + ", " + "" + sYear);
        stringDate = "" + sDate + " " + m + ", " + "" + sYear;
        alarmDate = "" + sDate + " " + m + " " + sYear;
        //for setting date and time manually
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog t = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        eTime = (TextView) findViewById(R.id.taskTime);
                        String tempMin = Integer.toString(minute);
                        if(minute % 10 == minute){
                            String temp = tempMin;
                            tempMin = "0" + temp;
                        }
                        alarmTime = hourOfDay + ":" + tempMin + ":00";
                        String s = "PM";
                        if (hourOfDay % 12 == hourOfDay) {
                            s = "AM";
                        }
                        finalAMPM = s;
                        if (hourOfDay != 12)
                            hourOfDay = hourOfDay % 12;
                        if (hourOfDay == 0) {
                            hourOfDay = 12;
                        }
                        if (minute % 10 == minute) {
                            stringTime = Integer.toString(hourOfDay) + ":0" + Integer.toString(minute) + " " + s;
                            eTime.setText(stringTime);
                        } else {
                            stringTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + " " + s;
                            eTime.setText(stringTime);
                        }
                    }
                }, hours, minutes, false);
                t.show();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///Date Picker Dialog
                DatePickerDialog d = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        eDate = (TextView) findViewById(R.id.taskDate);
                        String m = "Jan";
                        if (month == 0) {
                            m = "Jan";
                        } else if (month == 1) {
                            m = "Feb";
                        } else if (month == 2) {
                            m = "Mar";
                        } else if (month == 3) {
                            m = "Apr";
                        } else if (month == 4) {
                            m = "May";
                        } else if (month == 5) {
                            m = "Jun";
                        } else if (month == 6) {
                            m = "Jul";
                        } else if (month == 7) {
                            m = "Aug";
                        } else if (month == 8) {
                            m = "Sep";
                        } else if (month == 9) {
                            m = "Oct";
                        } else if (month == 10) {
                            m = "Nov";
                        } else if (month == 11) {
                            m = "Dec";
                        }
                        finalMonth = m;
                        stringDate = Integer.toString(dayOfMonth) + " " + m + ", " + Integer.toString(year);
                        alarmDate = Integer.toString(dayOfMonth) + " " + m + " " + Integer.toString(year);
                        eDate.setText(stringDate);
                    }
                }, sYear, sMonth, sDate);
                d.show();
            }
        });
    }

    private void populateViews() {
        TaskHelper taskHelper = new TaskHelper(this);
        SQLiteDatabase db = taskHelper.getReadableDatabase();
        Cursor c = db.query(TaskHelper.TASK_TABLE_NAME, null, "_id= " + id, null, null, null, null);
        if(c.moveToNext()){
            String title = c.getString(c.getColumnIndex(TaskHelper.TASK_TITLE));
            String taskDate = c.getString(c.getColumnIndex(TaskHelper.TASK_DATE));
            String taskTime = c.getString(c.getColumnIndex(TaskHelper.TASK_TIME));
            String description = c.getString(c.getColumnIndex(TaskHelper.TASK_DESCRIPTION));
            int isAlarm = c.getInt(c.getColumnIndex(TaskHelper.IS_ALARM));
            if(isAlarm == 1){
                Switch alarmSwitch = (Switch) findViewById(R.id.switchReminder);
                alarmSwitch.setChecked(true);
            }
            eTaskName = (EditText) findViewById(R.id.taskName);
            eTaskName.setText(title);
            TextView time = (TextView) findViewById(R.id.taskTime);
            TextView date = (TextView) findViewById(R.id.taskDate);
            time.setText(taskTime);
            date.setText(taskDate);
            TextView notes = (TextView) findViewById(R.id.notesEditText);
            notes.setText(description);
        }
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean b = true;
        boolean b1 = false;
        eTaskName = (EditText) findViewById(R.id.taskName);
        String s = eTaskName.getText().toString();
        if(s.length() == 0) {
            eTaskName.setError("Task can't be Empty!");
            b = false;
        }
        if(b){
            eDate = (TextView) findViewById(R.id.taskDate);
            eTime = (TextView) findViewById(R.id.taskTime);
            stringEpocTime = alarmDate + " " + alarmTime + " GMT+5:30";
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
            try {
                Date date = df.parse(stringEpocTime);
                epoch = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(reminderSwitch.isChecked()){
                b1 = true;
            }
            TaskHelper taskHelper = new TaskHelper(this);
            SQLiteDatabase db = taskHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TaskHelper.TASK_TITLE, s);
            cv.put(TaskHelper.TASK_DATE, eDate.getText().toString());
            cv.put(TaskHelper.TASK_TIME, eTime.getText().toString());
            cv.put(TaskHelper.TASK_DESCRIPTION, eTaskNotes.getText().toString());
            if(b1)
                cv.put(TaskHelper.IS_ALARM, 1);
            else
                cv.put(TaskHelper.IS_ALARM, 0);
            cv.put(TaskHelper.TASK_ALARM_TIME, epoch);
            if (id == -1) {
                db.insert(TaskHelper.TASK_TABLE_NAME, null, cv);
                if(b1){
                    String selectQuery = "SELECT  * FROM " + TaskHelper.TASK_TABLE_NAME;
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    cursor.moveToLast();
                    int passId = cursor.getInt(cursor.getColumnIndex(TaskHelper.TASK_COLUMN_ID));
                    setAlarm(passId);
                }
            } else {
                db.update(TaskHelper.TASK_TABLE_NAME, cv, "_id= " + id, null);
                if(b1){
                    setAlarm(id);
                }
            }
            setResult(Activity.RESULT_OK);
            db.close();
            finish();
        }
        return true;
    }

    private void setAlarm(int passId) {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent i2 = new Intent(this, AlarmReceiver.class);
        i2.putExtra("title", eTaskName.getText().toString());
        i2.putExtra("date", eDate.getText().toString());
        i2.putExtra("time", eTime.getText().toString());
        i2.putExtra("notes", eTaskNotes.getText().toString());
        i2.putExtra("id", passId);
        i2.putExtra("epochTime", epoch);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, passId, i2, 0);
        alarmManager.set(AlarmManager.RTC, epoch - 7 * 1000, pendingIntent);
    }

   
}
