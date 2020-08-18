package com.rodrigorossi.tripsfinalproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rodrigorossi.tripsfinalproject.model.Trip;
import com.rodrigorossi.tripsfinalproject.repository.TripDAO;

@Database(entities = {Trip.class}, version = 1, exportSchema = false)
public abstract class TripDatabase extends RoomDatabase {
    public abstract TripDAO tripDao();
    private static TripDatabase instance;

    public static TripDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (TripDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,TripDatabase.class,"trips.db").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
