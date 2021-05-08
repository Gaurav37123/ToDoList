package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

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

        {
            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            tvDateEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(WorkEntry.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            });
            setListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    date = dayOfMonth + "/" + month + "/" + year;
                    tvDateEntry.setText(date);
                }
            };
        }
        
            tvTimeEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(WorkEntry.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Hour = hourOfDay;
                            Minute = minute;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0,0,0,Hour,Minute);
                            tvTimeEntry.setText(DateFormat.format("hh:mm aa",calendar));
                        }
                    },12,0,false
                    );
                    timePickerDialog.updateTime(Hour,Minute);
                    timePickerDialog.show();
                }
            });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workDetails=etWorkDetails.getText().toString().trim();
                time = tvTimeEntry.getText().toString().trim();
                if(workDetails.length()!=0 && date !=null && time!=null)
                {
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

}