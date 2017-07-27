package com.app.assignment.Common;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import com.app.assignment.Main.MainActivity;
import com.app.assignment.Main.MainPresenter;
import com.app.assignment.Map.MapsActivity;
import com.app.assignment.repository.CityRepository;
import com.app.assignment.repository.local.AppDatabase;
import com.app.assignment.repository.local.CityDao;
import com.app.assignment.repository.remote.APIsFactory;
import com.app.assignment.repository.remote.APIsServices;

import static android.content.Context.MODE_PRIVATE;
import static com.app.assignment.Map.MapsActivity.LAT_KEY;
import static com.app.assignment.Map.MapsActivity.LNG_KEY;
import static com.app.assignment.Map.MapsActivity.NAME_KEY;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public class DependenciesManager {


    public static final DependenciesManager instance = new DependenciesManager();


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
     public static AppDatabase db;
     private static SharedPreferences sharedPreferences;

    private APIsServices services = APIsFactory.createInstance("http://assignment.pharos-solutions.de");

    private DependenciesManager() {

    }



    public static void init(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "database-task").build();
        sharedPreferences = context.getSharedPreferences("sharedprefs-task", Context.MODE_PRIVATE);
    }


    public CityDao cityDao(){
        return db.cityDao();
    }

    // region  city inject
    public CityRepository cityRepository() {
        return new CityRepository(services, cityDao());
    }

    public void inject(MainActivity mainActivity) {
        mainActivity.configureWith(new MainPresenter(cityRepository(), mainActivity, sharedPreferences));

    }

    // endregion


    // region  map inject


    public void inject(MapsActivity mapsActivity) {
        Intent intent = mapsActivity.getIntent();
        mapsActivity.confireWith(intent.getDoubleExtra(LAT_KEY, 0), intent.getDoubleExtra(LNG_KEY, 0), intent.getStringExtra(NAME_KEY));
    }


    //endregion
}
