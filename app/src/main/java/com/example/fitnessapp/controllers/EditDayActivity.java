package com.example.fitnessapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapp.R;
import com.example.fitnessapp.dao.CaloriesTrackerDBHelper;
import com.example.fitnessapp.model.Day;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditDayActivity extends AppCompatActivity {
    EditText breakfastCalories,lunchCalories,dinnerCalories,extraMeal;
    TextView date,breakfastResult,lunchResult,dinnerResult,totalCalories,extraMealResult;
    int textBreakfast,textLunch,textDinner, textTotal,textExtra,  currBreakfast, currLunch, currDinner,currExtra,currTotal;
    String finalDate;
    Date currDate;
    boolean isUpdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout_edit);
        CaloriesTrackerDBHelper dbHelper = CaloriesTrackerDBHelper.getInstance(this);
        date = findViewById(R.id.date);
        breakfastCalories = findViewById(R.id.breakfast_calories);
        breakfastResult = findViewById(R.id.breakfast_result);
        lunchCalories = findViewById(R.id.lunch_calories);
        lunchResult = findViewById(R.id.lunch_result);
        dinnerCalories = findViewById(R.id.dinner_calories);
        dinnerResult = findViewById(R.id.dinner_result);
        extraMeal = findViewById(R.id.extra_meal);
        extraMealResult = findViewById(R.id.extra_meal_result);
        totalCalories = findViewById(R.id.total_calories);
        int id = getIntent().getIntExtra("id",0);
        try {
            if (getIntent().getStringExtra("date") != null) {
                Day day = dbHelper.loadToday();
                finalDate = getIntent().getStringExtra("date");
                currBreakfast = day.getBreakfast() ;
                currLunch = day.getLunch() ;
                currDinner = day.getDinner();
                currExtra = day.getExtra() ;
                currTotal = day.getTotal();
                isUpdate = true;
            }else{
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                currDate = new Date();
                finalDate = sdf.format(currDate);
                currBreakfast = 0;
                currLunch = 0;
                currDinner = 0;
                currExtra = 0;
                currTotal = 0;
                isUpdate = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        date.setText(finalDate);
        breakfastResult.setText(String.valueOf(currBreakfast));
        lunchResult.setText(String.valueOf(currLunch));
        dinnerResult.setText(String.valueOf(currDinner));
        extraMealResult.setText(String.valueOf(currExtra));
        totalCalories.setText(String.valueOf(currTotal));
    }

    public void backPressed(View view){
        Intent intent = new Intent(this,CaloriesTrackerActivity.class);
        startActivity(intent);
    }

    public void onCalculate(View view) {

        textBreakfast = Integer.parseInt(breakfastResult.getText().toString());
        textLunch = Integer.parseInt(lunchResult.getText().toString());
        textDinner = Integer.parseInt(dinnerResult.getText().toString());
        textExtra = Integer.parseInt(extraMealResult.getText().toString());
        CaloriesTrackerDBHelper dbHelper = CaloriesTrackerDBHelper.getInstance(this);

        int breakfastNewResult = 0;
        int lunchNewResult = 0;
        int dinnerNewResult = 0;
        int extraNewResult = 0;
        int totalNewResult = 0;
        if (!totalCalories.getText().equals("")) {
            totalNewResult = Integer.parseInt(totalCalories.getText().toString());
        }


            try{
                breakfastNewResult += Integer.parseInt(breakfastCalories.getText().toString()) + textBreakfast;
                breakfastResult.setText(String.valueOf(breakfastNewResult));
            }catch (NumberFormatException e){
                Log.i("Log: ", "No value was added!");
            }
            try {
                lunchNewResult += Integer.parseInt(lunchCalories.getText().toString()) + textLunch;
                lunchResult.setText(String.valueOf(lunchNewResult));
            }catch (NumberFormatException e){
                Log.i("Log: ", "No value was added!");
            }
            try {
                dinnerNewResult += Integer.parseInt(dinnerCalories.getText().toString()) + textDinner;
                dinnerResult.setText(String.valueOf(dinnerNewResult));
            }catch (NumberFormatException e){
                Log.i("Log: ", "No value was added!");
            }

            try {
                extraNewResult += Integer.parseInt(extraMeal.getText().toString()) + textExtra;
                extraMealResult.setText(String.valueOf(extraNewResult));
            }catch (NumberFormatException e){
                Log.i("Log: ", "No value was added!");
            }

        totalNewResult += breakfastNewResult + lunchNewResult + dinnerNewResult + extraNewResult;
        Day day = new Day(finalDate, breakfastNewResult + currBreakfast, lunchNewResult + currLunch, dinnerNewResult + currDinner,
                extraNewResult + currExtra, totalNewResult);
        if (!isUpdate) {
            dbHelper.addDay(day);
        }else{
            dbHelper.updateDay(day);
        }
        totalCalories.setText(String.valueOf(totalNewResult));
        breakfastCalories.setText("");
        lunchCalories.setText("");
        dinnerCalories.setText("");
        extraMeal.setText("");
    }
}

