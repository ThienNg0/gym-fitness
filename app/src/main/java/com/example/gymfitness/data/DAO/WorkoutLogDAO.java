package com.example.gymfitness.data.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import com.example.gymfitness.data.entities.WorkoutLog;

import java.util.Date;

@Dao
public interface WorkoutLogDAO {
    @Query("SELECT * FROM WorkoutLog WHERE date = :date")
    WorkoutLog getWorkoutLogByDate(String date);

    @Upsert
    void insertWorkoutLog(WorkoutLog workoutLog);

    //check if the workout is already in the database on the same date
    @Query("SELECT COUNT(*) FROM WorkoutLog WHERE workout_name = :workout_name AND date = :date")
    WorkoutLog checkWorkout(String workout_name, Date date);
}