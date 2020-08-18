package com.rodrigorossi.tripsfinalproject.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rodrigorossi.tripsfinalproject.model.Trip;

import java.util.List;

@Dao
public interface TripDAO {

    @Insert
    long insert(Trip trip);

    @Delete
    void delete(Trip trip);

    @Update
    void update(Trip trip);

    @Query("SELECT * FROM trip WHERE id = :id")
    Trip findById(long id);

    @Query("SELECT * FROM trip ORDER BY id ASC")
    List<Trip> findAll();
}
