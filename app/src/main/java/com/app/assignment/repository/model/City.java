package com.app.assignment.repository.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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


}
