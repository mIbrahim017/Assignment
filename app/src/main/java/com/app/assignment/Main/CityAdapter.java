package com.app.assignment.Main;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.app.assignment.R;
import com.app.assignment.databinding.RecyclerItemBinding;
import com.app.assignment.repository.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.VH> implements Filterable {

    private List<City> originalCities = new ArrayList<>();

    private List<City> cities;


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_item, parent, false);
        return new VH(binding);
    }

    public void addCities(List<City> cities) {
        Log.e("adapter", "cities size : " + cities.size());

        originalCities.clear();
        originalCities.addAll(cities) ;
        this.cities = cities;
        notifyDataSetChanged();

    }

    public void addFilerCities(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.binding.setCity(cities.get(position));
    }

    @Override
    public Filter getFilter() {
        return new CitiesFilter(this, originalCities);
    }

    static class VH extends RecyclerView.ViewHolder {

        final RecyclerItemBinding binding;

        public VH(RecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


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
}
