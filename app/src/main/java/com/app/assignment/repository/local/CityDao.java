package com.app.assignment.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.app.assignment.repository.model.City;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

@Dao
public abstract class CityDao {


    @Query("SELECT * FROM CITY    WHERE  name LIKE '%' || :search || '%'  ORDER BY name ASC")
   public abstract   List<City> search(String search);


    @Query("SELECT * FROM CITY ORDER BY name  ASC")
    public abstract  Flowable<List<City>> loadCities();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<City> cities);


    @Query("DELETE FROM CITY")
    public  abstract void deleteAllCities();


    @Delete
    public abstract void deleteAll(City... cities);

}
