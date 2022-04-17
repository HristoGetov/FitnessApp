package com.example.fitnessapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.controllers.EditDayActivity;
import com.example.fitnessapp.dao.CaloriesTrackerDBHelper;
import com.example.fitnessapp.model.Day;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<Day> days;
    private Context context;

    public RecyclerViewAdapter(ArrayList<Day> days, Context context) {
        this.days = days;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_recycler_view_element,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Day date = new Day(days.get(position).getId(), days.get(position).getDate(),days.get(position).getBreakfast(),days.get(position).getLunch(),days.get(position).getDinner(),
                days.get(position).getExtra(),days.get(position).getTotal());
        holder.date.setText(date.getDate());
        CaloriesTrackerDBHelper dbHelper = CaloriesTrackerDBHelper.getInstance(context);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteItem(date);
                days.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditDayActivity.class);
                i.putExtra("date",date.getDate());
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView date;
        LinearLayout parent;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_id);
            parent = itemView.findViewById(R.id.parent);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
