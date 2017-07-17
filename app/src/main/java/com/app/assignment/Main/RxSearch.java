package com.app.assignment.Main;

import android.support.v7.widget.SearchView;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by mohamed ibrahim on 7/17/2017.
 */

public class RxSearch {
    public static Flowable<String> fromSearchView(@NonNull final SearchView searchView) {
        final BehaviorSubject<String> subject = BehaviorSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    subject.onNext(newText);
                }
                return true;
            }
        });

        return subject.toFlowable(BackpressureStrategy.DROP);
    }
}
