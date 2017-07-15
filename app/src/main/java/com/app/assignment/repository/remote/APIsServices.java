package com.app.assignment.repository.remote;

import com.app.assignment.repository.model.City;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mohamed.ibrahim on 7/5/2017.
 */

public interface APIsServices {
    @GET("/cities.json")
    Flowable<List<City>> getCities(@Query("page") int page);


}
