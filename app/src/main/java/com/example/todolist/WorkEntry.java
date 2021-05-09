package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class WorkEntry extends AppCompatActivity {

    TextView tvDateEntry,tvTimeEntry;
    int Hour,Minute;
    EditText etWorkDetails;
    Button btnSubmit;
    String date;
    String time;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_entry);
        tvDateEntry = findViewById(R.id.tvDateEntry);
        etWorkDetails = findViewById(R.id.etWorkDetails);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvTimeEntry = findViewById(R.id.tvTimeEntry);
        createNotificationChannel();

        tvDateEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDateFromUser();
                }
            });
        
        tvTimeEntry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getTimeFromUser();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workDetails=etWorkDetails.getText().toString().trim();
                time = tvTimeEntry.getText().toString().trim();
                if(workDetails.length()>100)
                    Toast.makeText(WorkEntry.this, "Word Limit is 100", Toast.LENGTH_SHORT).show();
                else if(workDetails.length()!=0 && date !=null && time!=null)
                {
                    setAlarm(date,time,workDetails);
                    Intent intent = new Intent();
                    intent.putExtra("date",date);
                    intent.putExtra("time",time);
                    intent.putExtra("work",workDetails);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else
                    Toast.makeText(WorkEntry.this, "Fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDateFromUser()
    {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR,year);
                calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                calendar1.set(Calendar.MONTH,month);
                date = String.valueOf(DateFormat.format("dd/MM/yyyy",calendar1));
                tvDateEntry.setText(date);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(WorkEntry.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK, setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
    private void getTimeFromUser()
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(WorkEntry.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Hour = hourOfDay;
                Minute = minute;
                Calendar calendar = Calendar.getInstance();
                calendar.set(0,0,0,Hour,Minute);
                tvTimeEntry.setText(DateFormat.format("hh:mm aa",calendar));
            }
        },12,0,false);
        timePickerDialog.show();
    }
    private void setAlarm(String date, String time,String workDetails)
    {

        Intent intent = new Intent(WorkEntry.this,ReminderBroadcast.class);
        intent.putExtra("message",workDetails);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(WorkEntry.this,(int)System.currentTimeMillis(),intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm aa");
        Date AlarmDate = new Date();
        try {
            AlarmDate = SDF.parse(date+"T"+time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(AlarmDate);
        calendar.add(Calendar.HOUR_OF_DAY,-1);
        AlarmDate = calendar.getTime();
        SDF.format(AlarmDate);
        alarmManager.set(AlarmManager.RTC_WAKEUP,AlarmDate.getTime(),pendingIntent);
        Toast.makeText(this, "Alarm Set in " + (-System.currentTimeMillis()+AlarmDate.getTime())+" millis", Toast.LENGTH_SHORT).show();

    }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("todolist","TODOLIST", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel for ToDoList");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}