package com.example.todolist.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todolist.R;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String message = intent.getStringExtra("message");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"todolist")
                .setSmallIcon(R.drawable.app_icon)
                .setContentTitle("Upcoming Task")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify((int)System.currentTimeMillis(),builder.build());

    }
}
