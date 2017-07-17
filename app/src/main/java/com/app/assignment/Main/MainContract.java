package com.app.assignment.Main;

import com.app.assignment.Common.BaseView;
import com.app.assignment.repository.model.City;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public interface MainContract {

    interface Presenter {
        void loadCities();

        void detach();

    }

    interface View  extends BaseView{
        void onCitiesLoaded(List<City> cities);


    }


}
