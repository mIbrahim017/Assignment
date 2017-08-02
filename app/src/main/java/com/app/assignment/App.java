package com.app.assignment;

import android.app.Application;

import com.app.assignment.Common.DependenciesManager;

import io.reactivex.Observable;
import io.reactivex.internal.operators.flowable.FlowableJust;

/**
 * Created by mohamed ibrahim on 7/13/2017.
 */

public class App extends Application {
    String s ="http://assignment.pharos-solutions.de/cities.json?page=100";




    @Override
    public void onCreate() {
        super.onCreate();
        DependenciesManager.init(this);



    }
}
