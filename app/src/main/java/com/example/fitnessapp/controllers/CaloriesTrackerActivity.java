package com.example.fitnessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.adapters.RecyclerViewAdapter;
import com.example.fitnessapp.dao.CaloriesTrackerDBHelper;
import com.example.fitnessapp.model.Day;

import java.util.ArrayList;

public class CaloriesTrackerActivity extends AppCompatActivity {

    CaloriesTrackerDBHelper dbHelper =  CaloriesTrackerDBHelper.getInstance(this);
    ArrayList<Day> days = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calories_tracker);
        CaloriesTrackerDBHelper dbHelper =  CaloriesTrackerDBHelper.getInstance(this);
        days = dbHelper.getAllDays();
        initRecyclerView();
    }

    public void pressBack(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(days,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onLoad(View view){
        Day day = dbHelper.loadToday();
        Intent intent = new Intent(this, EditDayActivity.class);
        if(day.getId()==0) {
            startActivity(intent);
        }else{
            String breakf = String.valueOf(day.getBreakfast());
            String lunch = String.valueOf(day.getLunch());
            String dinner = String.valueOf(day.getDinner());
            String extra = String.valueOf(day.getExtra());
            String total = String.valueOf(day.getTotal());
            intent.putExtra("id", day.getId());
            intent.putExtra("date", day.getDate());
            intent.putExtra("breakfast", breakf);
            intent.putExtra("lunch", lunch);
            intent.putExtra("dinner", dinner);
            intent.putExtra("extra", extra);
            intent.putExtra("total", total);
            startActivity(intent);
        }



    }

}
