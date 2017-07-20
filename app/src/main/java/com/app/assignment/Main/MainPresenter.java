package com.app.assignment.Main;

import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.app.assignment.repository.CityRepository;
import com.app.assignment.repository.model.City;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private CompositeDisposable mDisposable = new CompositeDisposable();


    private SharedPreferences sharedPreferences;
    private CityRepository repo;
    private MainContract.View view;
    private int currentPage = 1;





    public MainPresenter(CityRepository repo, final MainContract.View view, SharedPreferences sharedPreferences) {
        this.repo = repo;
        this.view = view;
        this.sharedPreferences = sharedPreferences;

        currentPage = sharedPreferences.getInt("currentPage", 1);

        mDisposable.add(
                repo.getCitiesCache()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<City>>() {
                            @Override
                            public void accept(@NonNull List<City> cities) throws Exception {


                                Log.e("MainPresenter", Thread.currentThread().getName());

                                if (cities != null && cities.size() > 0) showList(cities);
                                else loadCities();

                            }


                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                if (throwable != null) throwable.printStackTrace();
                                Log.e("MainPresenter", Thread.currentThread().getName());
                                showError("Some thing wrong try later");

                            }
                        })
        );


    }

    private void showError(String msg) {
        if (view == null) return;
        view.onFail(msg);
    }

    private void showList(List<City> cities) {
        if (view == null) return;
        view.onCitiesLoaded(cities);


    }

    @Override
    public void loadCities() {

        if (view.checkNetworkConnection()) {
            mDisposable.add(
                    repo.getCitiesRemotely(currentPage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<List<City>>() {
                                @Override
                                public void accept(@NonNull List<City> cities) throws Exception {
                                    currentPage += 1;
                                    sharedPreferences.edit().putInt("currentPage", currentPage).apply();

                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(@NonNull Throwable throwable) throws Exception {
                                    if (throwable != null) throwable.printStackTrace();
                                    showError("Some thing wrong try later");

                                }
                            })
            );
        } else {
            showError("No Internet connection");
        }

    }

    @WorkerThread
    @Override
    public List<City> searchCities(String prefix) {
        return repo.search(prefix);
    }

    @Override
    public void detach() {
        repo = null;
        view = null;
        mDisposable.clear();
        mDisposable = null;
    }


}
