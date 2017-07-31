package com.example.sparshgupta.taskit;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.sparshgupta.taskit.R.mipmap.ic_launcher;


public class AlarmReceiver extends BroadcastReceiver {


    SharedPreferences notificationID;
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
//        String date = intent.getStringExtra("date");
//        String time = intent.getStringExtra("time");
        String notes = intent.getStringExtra("notes");
        int id = intent.getIntExtra("id", -1);
        Toast.makeText(context, id + "", Toast.LENGTH_SHORT).show();
//        TaskHelper taskHelper = new TaskHelper(context);
//        SQLiteDatabase db = taskHelper.getReadableDatabase();
//        Cursor c = db.query(TASK_TABLE_NAME, null, "_id= " + id, null, null, null, null);
//        String title = null;
//        String notes = null;
//        if(c.moveToNext()){
//            title = c.getString(c.getColumnIndex(TaskHelper.TASK_TITLE));
//            notes = c.getString(c.getColumnIndex(TaskHelper.TASK_DESCRIPTION));
//        }
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
//        db.close();
        if(title != null) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setContentTitle(title)
                            .setSmallIcon(ic_launcher)
                            .setContentText(notes);
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(id, mBuilder.build());
        }


    }
}
