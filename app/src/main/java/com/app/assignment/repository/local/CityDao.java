package com.app.assignment.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.app.assignment.repository.model.City;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

@Dao
public interface CityDao {

    @Query("SELECT * FROM CITY ORDER BY name , country  LIMIT 50")
    Flowable<List<City>> loadCities();


    @Insert
    void insertAll(List<City> cities);


    @Query("DELETE FROM CITY")
    void deleteAllCities();


    @Delete
    void deleteAll(City... cities);

}
