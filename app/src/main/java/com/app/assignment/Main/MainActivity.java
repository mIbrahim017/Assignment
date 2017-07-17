package com.app.assignment.Main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.app.assignment.Common.BaseActivity;
import com.app.assignment.Common.DependenciesManager;
import com.app.assignment.R;
import com.app.assignment.repository.model.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MainContract.Presenter presenter;
    private CityAdapter cityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        DependenciesManager.instance.inject(this);


    }


    private void initToolbar() {


        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(ContextCompat.getColor(this ,android.R.color.white));
    }

    @OnClick(R.id.loadmore)
    void onLoadMore() {
        if (presenter != null) presenter.loadCities();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) presenter.detach();
        super.onDestroy();
    }

    public void configureWith(MainContract.Presenter presenter) {
        this.presenter = presenter;
        cityAdapter = new CityAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cityAdapter);

//        this.presenter.loadCities();
    }

    @Override
    public void onCitiesLoaded(List<City> cities) {
        cityAdapter.addCities(cities);
    }

}
