package com.app.assignment.repository;

import android.util.Log;

import com.app.assignment.repository.local.CityDao;
import com.app.assignment.repository.model.City;
import com.app.assignment.repository.remote.APIsServices;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public class CityRepository {

    private final APIsServices services;
    private final CityDao db;


    public CityRepository(APIsServices services, CityDao db) {
        this.services = services;
        this.db = db;
    }


    public Flowable<List<City>> getCities(final int lastId) {

        final Flowable<List<City>> cache = db.loadCities();

        final Flowable<List<City>> network = services.getCities(lastId).doOnNext(new Consumer<List<City>>() {
            @Override
            public void accept(@NonNull List<City> cities) throws Exception {
                db.insertAll(cities);
            }
        }).concatWith(cache);


        return cache.flatMap(new Function<List<City>, Publisher<List<City>>>() {
            @Override
            public Publisher<List<City>> apply(@NonNull List<City> cities) throws Exception {
                if (cities == null) return network;
                if (cities.size() == 0) return network;
                return cache;
            }
        });


    }


}
