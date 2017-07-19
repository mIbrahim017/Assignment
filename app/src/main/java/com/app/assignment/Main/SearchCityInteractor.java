package com.app.assignment.Main;

import android.text.TextUtils;

import com.app.assignment.repository.model.City;

import java.util.ArrayList;
import java.util.List;

import static com.app.assignment.Common.Utils.isEmpty;

/**
 * Created by mohamed ibrahim on 7/19/2017.
 */

public class SearchCityInteractor {
    private final List<City> cities = new ArrayList<>();


    public SearchCityInteractor(List<City> cities) {
        if (cities != null) this.cities.addAll(cities);
    }


    public List<City> search(String prefix) {


        if (isEmpty(prefix)) return cities;

        final List<City> filteredData = new ArrayList<>();


        final String filterPattern = prefix.toLowerCase().trim();

        for (final City city : cities) {
            if (city.name.toLowerCase().contains(filterPattern)) {
                filteredData.add(city);
            }
        }
        return filteredData;
    }
}

