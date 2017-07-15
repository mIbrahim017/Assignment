package com.app.assignment.Common;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import static android.text.TextUtils.isEmpty;

/**
 * Created by mohamed ibrahim on 7/14/2017.
 */

public class BaseActivity extends LifecycleActivity {
    private ProgressDialog progressDialog;

    public void showProgressBar() {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this);
            }

            if (!progressDialog.isShowing())
                progressDialog.show();
        } catch (Throwable e) {

        }
    }

    public void hideProgressBar() {
        try {
            if ((progressDialog != null) && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Throwable e) {
            // Handle or log or ignore
        } finally {
            progressDialog = null;
        }

    }

    public boolean checkNetworkConnection() {
        if (!isNetworkConnected()) {
            showNotification("No Internet Connection!");
            return false;
        }
        return true;
    }


    public boolean isNetworkConnected() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Throwable e) {
            return false;
        }
    }

    public void onFail(String msg) {
        showNotification(msg);
    }

    public void onFail(int resId) {
        showNotification(getString(resId));
    }


    public void showNotification(String message) {
        if (isEmpty(message)) return;
        final View mRootView = findViewById(android.R.id.content);
        if (mRootView == null) return;
        Snackbar mSnackBar = Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG);

        Snackbar.SnackbarLayout sbView = (Snackbar.SnackbarLayout) mSnackBar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        if (textView == null) return;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        textView.setText(message);
        mSnackBar.show();
    }








}
