package com.app.assignment.repository;

import com.app.assignment.repository.local.CityDao;
import com.app.assignment.repository.model.City;
import com.app.assignment.repository.remote.APIsServices;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.completable.CompletableFromAction;

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


    public Flowable<List<City>> getCitiesCache() {
        return db.loadCities();
    }


    public Flowable<List<City>> getCitiesRemotely(final int page) {
        return services.getCities(page).doOnNext(new Consumer<List<City>>() {
            @Override
            public void accept(@NonNull List<City> cities) throws Exception {
                db.insertAll(cities);
            }
        });
    }

//    public Completable getCitiesRemotely_(final int page) {
//        return new CompletableFromAction(new Action() {
//            @Override
//            public void run() throws Exception {
//
//                services.getCities(page).doOnNext(new Consumer<List<City>>() {
//                    @Override
//                    public void accept(@NonNull List<City> cities) throws Exception {
//                        db.insertAll(cities);
//                    }
//                });
//            }
//        });
//
//
//    }


//    public Flowable<List<City>> getCities(final int currentPage) {
//
//
//        final Flowable<List<City>> cache = db.loadCities();
//
//
//        final Flowable<List<City>> network = services.getCities(currentPage).doOnNext(new Consumer<List<City>>() {
//            @Override
//            public void accept(@NonNull List<City> cities) throws Exception {
//                db.insertAll(cities);
//            }
//        }).concatWith(cache);
//
//        if (currentPage > 1) return network;
//
//
//        return cache.flatMap(new Function<List<City>, Publisher<List<City>>>() {
//            @Override
//            public Publisher<List<City>> apply(@NonNull List<City> cities) throws Exception {
//                if (cities == null) return network;
//                if (cities.size() == 0) return network;
//                return cache;
//            }
//        });
//
//
//    }


}
