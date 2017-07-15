package com.app.assignment.Common;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public interface BaseView {


    boolean checkNetworkConnection();

    void showProgressBar();

    void hideProgressBar();

    void onFail(String message);

    void onFail(int resId);


}
