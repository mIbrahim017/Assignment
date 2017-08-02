package com.app.assignment.Main;

import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.app.assignment.repository.CityRepository;
import com.app.assignment.repository.model.City;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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

    private Flowable<Boolean> connectivity;


    public MainPresenter(CityRepository repo, final MainContract.View view,
                         SharedPreferences sharedPreferences, Flowable<Boolean> connectivity) {
        this.connectivity = connectivity;
        this.repo = repo;
        this.view = view;
        this.sharedPreferences = sharedPreferences;

        currentPage = sharedPreferences.getInt("currentPage", 1);


        mDisposable.add(connectivity.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                if (view != null) view.isNetworkAvailable(aBoolean);
            }
        }));

        mDisposable.add(repo.getCitiesCache()
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


        mDisposable.add(
                connectivity.filter(new Predicate<Boolean>() {
                    @Override
                    public boolean test(@NonNull Boolean aBoolean) throws Exception {
                        return aBoolean;
                    }
                }).flatMap(new Function<Boolean, Publisher<List<City>>>() {
                    @Override
                    public Publisher<List<City>> apply(@NonNull Boolean aBoolean) throws Exception {
                        return repo.getCitiesRemotely(currentPage);
                    }
                }).subscribeOn(Schedulers.io())
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
