package com.app.assignment.Main;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.assignment.R;
import com.app.assignment.databinding.RecyclerItemBinding;
import com.app.assignment.repository.model.City;

import java.util.List;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.VH> {

    private List<City> cities;


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recycler_item, parent, false);
        return new VH(binding);
    }

    public void addCities(List<City> cities) {
        Log.e("adapter", "cities size : " + cities.size());
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

    static class VH extends RecyclerView.ViewHolder {

        final RecyclerItemBinding binding;

        public VH(RecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
