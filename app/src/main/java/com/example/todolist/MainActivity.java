package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.classes.Work;
import com.example.todolist.classes.WorkAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements WorkAdapter.ItemClicked {

    ArrayList<Work> WorkList = new ArrayList<>();
    FloatingActionButton fab;
    static final String MY_PREF_NAME = "workData";
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.WorkList);

        String WorkString;
        SharedPreferences prefs = getSharedPreferences(MY_PREF_NAME,MODE_PRIVATE);
        WorkString = prefs.getString("work_string","");
        if(WorkString.compareTo("")!=0)
            DecryptList(WorkString);

        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new WorkAdapter(this,WorkList);
        recyclerView.setAdapter(myAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WorkEntry.class);
                startActivityForResult(intent,1);
            }
        });
        
    }

    private void DecryptList(String workString)
    {
        String[] oneClassItem = workString.split(";");
        for(int i=0;i<oneClassItem.length;i++)
            CreateEntry(oneClassItem[i]);
    }

    private void CreateEntry(String Substring)
    {
        String[] ClassData = Substring.split("<");

        WorkList.add(new Work(ClassData[0],ClassData[1],ClassData[2]));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode ==RESULT_OK)
        {
            WorkList.add(new Work(data.getStringExtra("date"),data.getStringExtra("time"),data.getStringExtra("work")));
            SortList();
        }
        myAdapter.notifyDataSetChanged();
    }

    private void SortList() {
        Collections.sort(WorkList, new Comparator<Work>() {
            @Override
            public int compare(Work o1, Work o2) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm aa");
                Date date1= null;
                try {
                    date1 = format.parse(o1.getDate()+"T"+o1.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date2= null;
                try {
                    date2 = format.parse(o2.getDate()+"T"+o2.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });
    }

    @Override
    public void onDeleteClick(int index) {
        WorkList.remove(index);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        String WorkString="";
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME,MODE_PRIVATE).edit();
        for(int i=0;i<WorkList.size();i++)
        {
            WorkString = WorkString+WorkList.get(i).getDate()+"<"+WorkList.get(i).getTime()+"<"+WorkList.get(i).getWork()+";";
        }
        editor.remove("work_string");
        editor.putString("work_string",WorkString);
        editor.commit();
    }
}