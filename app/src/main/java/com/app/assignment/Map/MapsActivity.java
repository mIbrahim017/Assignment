package com.app.assignment.Map;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.app.assignment.Common.DependenciesManager;
import com.app.assignment.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    public static final String LAT_KEY = "lat_key";
    public static final String LNG_KEY = "lng_key";
    public static final String NAME_KEY = "name_key";


    private double lat, lng;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        DependenciesManager.instance.inject(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(location).title(name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));


    }


    public static void navigate(Context context, double lat, double lng, String name) {
        if (context == null) return;
        final Intent i = new Intent(context, MapsActivity.class);
        i.putExtra(LAT_KEY, lat);
        i.putExtra(LNG_KEY, lng);
        i.putExtra(NAME_KEY, name);
        context.startActivity(i);

    }

    public void confireWith(double lat, double lng, String name) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }
}
