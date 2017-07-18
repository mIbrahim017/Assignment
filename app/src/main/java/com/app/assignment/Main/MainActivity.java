package com.app.assignment.Main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.assignment.Common.BaseActivity;
import com.app.assignment.Common.DependenciesManager;
import com.app.assignment.Map.MapsActivity;
import com.app.assignment.R;
import com.app.assignment.repository.model.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MainContract.Presenter presenter;
    private CityAdapter cityAdapter;
    private SearchView searchView;


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
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
    }


    @Override
    protected void onDestroy() {
        if (presenter != null) presenter.detach();
        super.onDestroy();
    }

    public void configureWith(MainContract.Presenter presenter) {
        this.presenter = presenter;
        initRecyclerView();
        initAdapter();

    }


    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }


    private Runnable removeLoaderRunnable = new Runnable() {
        @Override
        public void run() {
            cityAdapter.addLoadMoreItem();
            presenter.loadCities();
        }
    };

    private void initAdapter() {
        cityAdapter = new CityAdapter(this ,recyclerView);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.setOnLoadMoreListener(new CityAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(removeLoaderRunnable);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        cityAdapter.setSearchView(searchView);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cityAdapter.getFilter().filter(newText);
                return true;
            }
        });


        return true;
    }


    @Override
    public void onCitiesLoaded(List<City> cities) {
        cityAdapter.removeLoadMoreItem();
        cityAdapter.addCities(cities);
        cityAdapter.setLoaded();
    }

}
