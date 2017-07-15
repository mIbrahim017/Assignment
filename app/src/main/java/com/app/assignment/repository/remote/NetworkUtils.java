package com.app.assignment.repository.remote;

import android.util.Log;

import com.app.assignment.Common.BaseView;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by mohamed.ibrahim on 7/5/2017.
 */

public class NetworkUtils {
    public static <T> void makeNetworkRequest(BaseView view, Call<T> call, Callback<T> callback) {


        try {
            if (view == null) return;

            if (view.checkNetworkConnection()) {
                view.showProgressBar();
                call.enqueue(callback);
            }

        } catch (Throwable e) {

            Log.e("NetworkUtils" , e.getMessage());
        }
    }

    public static void showError(BaseView view) {
        if (view != null) view.onFail("Network error happened");
    }

    public static void showError(BaseView view, int resId) {
        if (view == null) return;
        view.onFail(resId);
    }


    public static void showError(BaseView view, String resId) {
        if (view == null) return;
        view.onFail(resId);
    }

    public static void checkNetworkError(Throwable e, BaseView view) {
        if (view == null) return;
        view.hideProgressBar();
        view.onFail(e.getMessage());

    }

}
