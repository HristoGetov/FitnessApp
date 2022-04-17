package com.example.fitnessapp.model;

public class Exercise {

    private int id;
    private String exerciseName;
    private int series;
    private int repetitions;
    private String type;


    public Exercise() {
    }

    public Exercise(int id, String exerciseName, int series, int repetitions, String type) {
        this.id = id;
        this.exerciseName = exerciseName;
        this.series = series;
        this.repetitions = repetitions;
        this.type = type;
    }

    public Exercise(String exerciseName, int series, int repetitions, String type) {
        this.exerciseName = exerciseName;
        this.series = series;
        this.repetitions = repetitions;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
