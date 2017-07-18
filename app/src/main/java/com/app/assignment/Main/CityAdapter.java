package com.app.assignment.Main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.app.assignment.Map.MapsActivity;
import com.app.assignment.R;
import com.app.assignment.databinding.RecyclerItemBinding;
import com.app.assignment.repository.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed ibrahim on 7/18/2017.
 */

public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;


    private List<City> originalCities = new ArrayList<>();

    private List<City> cities;
    private SearchView searchView;

    private Context context;

    public CityAdapter(Context context, RecyclerView mRecyclerView) {

        this.context = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (searchView != null && !searchView.isIconified()) return;
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }


    @Override
    public int getItemViewType(int position) {
        return cities.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            final RecyclerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_item, parent, false);
            return new VH(binding);
        } else if (viewType == VIEW_TYPE_LOADING) {
            return new LoadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_load, parent, false));


        }
        return null;
    }



    public void onCityItemClicked() {
        Toast.makeText(context, "clickd", Toast.LENGTH_SHORT).show();

    }



    public void onCityItemClicked(City city) {
        if (city == null) return;
        MapsActivity.navigate(context, city.coord.lat, city.coord.lon , city.name);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VH) {

            VH vh = (VH) holder;
            vh.binding.setCallback(this);
            vh.binding.setCity(cities.get(position));

        } else if (holder instanceof LoadHolder) {

        }
    }

    public void setLoaded() {
        isLoading = false;
    }


    public void addCities(List<City> cities) {
        Log.e("adapter", "cities size : " + cities.size());

        originalCities.clear();
        originalCities.addAll(cities);
        this.cities = cities;
        notifyDataSetChanged();

    }

    public void addFilerCities(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return new CitiesFilter(this, originalCities);
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }


    public void addLoadMoreItem() {
        if (cities == null) return;
        cities.add(null);
        notifyItemInserted(cities.size() - 1);

    }


    public void removeLoadMoreItem() {
        if (cities == null) return;
        cities.add(null);
        notifyItemInserted(cities.size() - 1);
    }

    public void setSearchView(SearchView searchView) {
        this.searchView = searchView;
    }

    interface OnLoadMoreListener {
        void onLoadMore();
    }


    private static class CitiesFilter extends Filter {

        private final CityAdapter listAdapter;

        private final List<City> originalData;
        private List<City> filteredData;


        public CitiesFilter(CityAdapter listAdapter, List<City> originalData) {
            this.listAdapter = listAdapter;
            this.originalData = originalData;
            this.filteredData = new ArrayList<>();
        }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredData.clear();
            final FilterResults results = new FilterResults();


            if (TextUtils.isEmpty(constraint.toString())) {
                filteredData.addAll(originalData);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final City city : originalData) {
                    // set condition for filter here
                    if (city.name.toLowerCase().contains(filterPattern)) {
                        filteredData.add(city);
                    }
                }
            }

            results.values = filteredData;
            results.count = filteredData.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listAdapter.addFilerCities((List<City>) filterResults.values);

        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    static class VH extends RecyclerView.ViewHolder {

        final RecyclerItemBinding binding;

        public VH(RecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
