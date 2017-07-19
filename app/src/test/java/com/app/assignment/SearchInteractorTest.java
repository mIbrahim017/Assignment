package com.app.assignment;

import com.app.assignment.Main.SearchCityInteractor;
import com.app.assignment.repository.model.City;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mohamed ibrahim on 7/19/2017.
 */

public class SearchInteractorTest {

    private final List<City> cities = Arrays.asList(
            City.create("Middeldijk", "NL"),
            City.create("Understed", "DK"),
            City.create("Skodsborg", "DK"),
            City.create("Smidstrup", "DK"),
            City.create("Rastnik", "BG"),
            City.create("Murava", "LT")
    );

    @Test
    public void testSearchNullCities() {
        SearchCityInteractor searchCityInteractor = new SearchCityInteractor(null);
        Assert.assertEquals(searchCityInteractor.search(null).size(), 0);

    }


    @Test
    public void testSearchNullPrefix() {
        SearchCityInteractor searchCityInteractor = new SearchCityInteractor(cities);
        Assert.assertEquals(searchCityInteractor.search(null).size(), cities.size());

    }

    @Test
    public void testSearchEmptyPrefix() {
        SearchCityInteractor searchCityInteractor = new SearchCityInteractor(cities);
        Assert.assertEquals(searchCityInteractor.search("").size(), cities.size());

    }

    @Test
    public void testSearchValidPrefix() {
        SearchCityInteractor searchCityInteractor = new SearchCityInteractor(cities);
        Assert.assertEquals(searchCityInteractor.search("Middeldijk").size(), 1);

    }


    @Test
    public void testSearch_A_Prefix() {
        SearchCityInteractor searchCityInteractor = new SearchCityInteractor(cities);
        Assert.assertEquals(searchCityInteractor.search("A").size(), 2);

    }


    @Test
    public void testSearch_D_Prefix() {
        SearchCityInteractor searchCityInteractor = new SearchCityInteractor(cities);
        Assert.assertEquals(searchCityInteractor.search("d").size(), 4);

    }

}
