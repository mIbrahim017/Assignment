package com.app.assignment;

import android.arch.persistence.room.Room;

import com.app.assignment.Common.DependenciesManager;
import com.app.assignment.repository.local.AppDatabase;
import com.app.assignment.repository.local.CityDao;
import com.app.assignment.repository.model.City;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import static com.app.assignment.Common.DependenciesManager.instance;

/**
 * Created by mohamed ibrahim on 7/20/2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SearchDatabaseTest {

    private final List<City> cities = Arrays.asList(
            City.create(1,"Middeldijk", "NL"),
            City.create(2,"Understed", "DK"),
            City.create(3,"Skodsborg", "DK"),
            City.create(4,"Smidstrup", "DK"),
            City.create(5,"Rastnik", "BG"),
            City.create(6,"Murava", "LT")
    );
    private CityDao cityDao;

    @Before
    public void setup() {
        DependenciesManager.db = Room.databaseBuilder(RuntimeEnvironment.application, AppDatabase.class, "database-task")
                .allowMainThreadQueries()
                .build();
        cityDao = instance.cityDao();
        cityDao.insertAll(cities);
    }


    @Test
    public void testNullValue() {

        Assert.assertEquals(cityDao.search("").size(), 6);
        Assert.assertEquals(cityDao.search("Murava").size(), 1);
        Assert.assertEquals(cityDao.search("Rastnik").size(), 1);
        Assert.assertEquals(cityDao.search("m").size(), 3);
        Assert.assertEquals(cityDao.search("u").size(), 3);

//
    }


}
