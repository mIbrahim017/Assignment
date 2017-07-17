package com.app.assignment.Common;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.assignment.Main.MainActivity;
import com.app.assignment.Main.MainPresenter;
import com.app.assignment.repository.CityRepository;
import com.app.assignment.repository.local.AppDatabase;
import com.app.assignment.repository.remote.APIsFactory;
import com.app.assignment.repository.remote.APIsServices;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public class DependenciesManager {


    public static final DependenciesManager instance = new DependenciesManager();


    private static AppDatabase db;
    private static SharedPreferences sharedPreferences;

    private APIsServices services = APIsFactory.createInstance("http://assignment.pharos-solutions.de");

    private DependenciesManager() {

    }

    public static void init(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "database-task").build();
        sharedPreferences = context.getSharedPreferences("sharedprefs-task", Context.MODE_PRIVATE);
    }


    // region  city inject
    public CityRepository cityRepository() {
        return new CityRepository(services, db.cityDao());
    }

    public void inject(MainActivity mainActivity) {
        mainActivity.configureWith(new MainPresenter(cityRepository(), mainActivity , sharedPreferences));

    }

    // endregion
}
