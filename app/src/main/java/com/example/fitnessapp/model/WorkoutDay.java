package com.example.fitnessapp.model;

import java.util.ArrayList;

public class WorkoutDay {

    private int id;
    private String workoutDay;
    private ArrayList<Exercise> exercise;

    public WorkoutDay(int id, String workoutDay, ArrayList<Exercise> exercise) {
        this.id = id;
        this.workoutDay = workoutDay;
        this.exercise = exercise;
    }

    public WorkoutDay(String workoutDay, ArrayList<Exercise> exercise) {
        this.workoutDay = workoutDay;
        this.exercise = exercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkoutDay() {
        return workoutDay;
    }

    public void setWorkoutDay(String workoutDay) {
        this.workoutDay = workoutDay;
    }

    public ArrayList<Exercise> getExercise() {
        return exercise;
    }

    public void setExercise(ArrayList<Exercise> exercise) {
        this.exercise = exercise;
    }
}

