package com.example.fitnessapp.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.fitnessapp.model.Day;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaloriesTrackerDBHelper extends SQLiteOpenHelper {

    private static CaloriesTrackerDBHelper instance;
    public static final String DAYS_TABLE = "DAYS";
    public static final String DAY_ID = "day_id";
    public static final String DATE = "date";
    public static final String BREAKFAST = "breakfast";
    public static final String LUNCH = "lunch";
    public static final String DINNER = "dinner";
    public static final String EXTRA = "extra";
    public static final String TOTAL = "total";

    private CaloriesTrackerDBHelper(@Nullable Context context) {
        super(context, "calories.db",null,1);
    }

    public static CaloriesTrackerDBHelper getInstance(Context context){
        if (instance == null){
            instance = new CaloriesTrackerDBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + DAYS_TABLE + " ( " + DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DATE + " VARCHAR(50), " + BREAKFAST + " INTEGER, " + LUNCH + " INTEGER, " + DINNER + " INTEGER, " + EXTRA + " INTEGER, " + TOTAL + " INTEGER)";

        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addDay(Day day){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DATE,day.getDate());
        cv.put(BREAKFAST,day.getBreakfast());
        cv.put(LUNCH,day.getLunch());
        cv.put(DINNER, day.getDinner());
        cv.put(EXTRA, day.getExtra());
        cv.put(TOTAL,day.getTotal());
        database.insert(DAYS_TABLE,null,cv);
    }

    public Day loadToday(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        String selectCurrentDay = "Select * from " + DAYS_TABLE + " where date like '%" + sdf.format(date)+ "%';";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectCurrentDay,null);
        Day day = new Day();
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String currDate = cursor.getString(1);
                String breakfast = cursor.getString(2);
                String lunch = cursor.getString(3);
                String dinner = cursor.getString(4);
                String extra = cursor.getString(5);
                String total = cursor.getString(6);
                day = new Day(id,currDate,Integer.parseInt(breakfast),
                        Integer.parseInt(lunch),Integer.parseInt(dinner),
                        Integer.parseInt(extra),Integer.parseInt(total));
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        database.close();
        return day;
    }

    public ArrayList<Day> getAllDays(){

            ArrayList<Day> allDays = new ArrayList<>();

            String getAllItems = "SELECT * FROM " + DAYS_TABLE;
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(getAllItems,null);
            if(cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String date = cursor.getString(1);
                    int breakfast = cursor.getInt(2);
                    int lunch = cursor.getInt(3);
                    int dinner = cursor.getInt(4);
                    int extra = cursor.getInt(5);
                    int total = cursor.getInt(6);
                    Day day = new Day(id,date,breakfast,lunch,dinner,extra,total);
                    allDays.add(day);
                } while (cursor.moveToNext());
            }else{

            }
            cursor.close();
            database.close();
            return allDays;
    }

    public void updateDay(Day day){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String [] args = {day.getDate()};
        cv.put(BREAKFAST,day.getBreakfast());
        cv.put(LUNCH,day.getLunch());
        cv.put(DINNER,day.getDinner());
        cv.put(EXTRA,day.getExtra());
        cv.put(TOTAL,day.getTotal());
        db.update(DAYS_TABLE,cv," date = ? " ,args);
        db.close();
    }

    public boolean deleteItem(Day day){

        String deleteItem= "Delete from " + DAYS_TABLE + " where " + DAY_ID + " = " + day.getId();
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(deleteItem, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }
}
