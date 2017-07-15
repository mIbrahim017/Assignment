package com.app.assignment.Main;

import com.app.assignment.repository.CityRepository;
import com.app.assignment.repository.model.City;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private CityRepository repo;
    private MainContract.View view;
    private int lastId = 1;
    private int currentPage = 1;
    private Disposable subscribe;


    public MainPresenter(CityRepository repo, MainContract.View view) {
        this.repo = repo;
        this.view = view;
    }


    @Override
    public void loadCities() {

        subscribe = repo.getCities(lastId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<City>>() {
                    @Override
                    public void accept(@NonNull List<City> cities) throws Exception {
                        view.onCitiesLoaded(cities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        view.onError(throwable.getMessage());
                    }
                });


    }

    @Override
    public void detach() {
        repo = null;
        view = null;

        if (subscribe != null) {
            subscribe.dispose();
            subscribe = null;
        }
    }
}
