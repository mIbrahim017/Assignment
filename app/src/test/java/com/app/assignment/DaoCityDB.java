package com.app.assignment;

import com.app.assignment.repository.local.CityDao;
import com.app.assignment.repository.model.City;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by mohamed ibrahim on 7/19/2017.
 */

public class DaoCityDB implements CityDao {
    @Override
    public Flowable<List<City>> loadCities() {
        return null;
    }

    @Override
    public void insertAll(List<City> cities) {

    }

    @Override
    public void deleteAllCities() {

    }

    @Override
    public void deleteAll(City... cities) {

    }
}
