package com.example.todolist;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String message = intent.getStringExtra("message");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"todolist")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Pending Task")
                .setContentText(message)
                .setGroup("ToDoList")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify((int)System.currentTimeMillis(),builder.build());

    }
}
