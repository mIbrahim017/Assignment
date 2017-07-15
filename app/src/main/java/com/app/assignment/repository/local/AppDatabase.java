package com.app.assignment.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.app.assignment.repository.model.City;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

@Database(entities = {City.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao cityDao();

}
