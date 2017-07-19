package com.app.assignment.repository.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.provider.Settings;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */


@Entity(tableName = "CITY")
public class City {
    @PrimaryKey
    public long _id;
    @ColumnInfo
    public String country;
    @ColumnInfo
    public String name;
    @Embedded
    public Coord coord;



    public static City create(String name , String country){
        City c  = new City();
        c._id = System.currentTimeMillis();
        c.name = name ;
        c.country = country ;
        Coord coord = new Coord();
        coord.lat = 30.111;
        coord.lon = 29.111;
        c.coord =coord ;
        return  c ;

    }


}
