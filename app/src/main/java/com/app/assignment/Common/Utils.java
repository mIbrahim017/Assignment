package com.app.assignment.Common;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by mohamed ibrahim on 7/13/2017.
 */

public final class Utils {
    private Utils() {

    }


    @BindingAdapter({"bind:lat", "bind:lng"})
    public static void loadGoogleImage(ImageView view, double lat, double lng) {

        final String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" +
                lat +
                "," +
                lng +
                "&zoom=13&size=600x300";

        Picasso.with(view.getContext()).load(imageUrl).into(view);
    }


}
