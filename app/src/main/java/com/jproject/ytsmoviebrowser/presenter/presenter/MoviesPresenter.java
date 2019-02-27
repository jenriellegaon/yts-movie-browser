package com.jproject.ytsmoviebrowser.presenter.presenter;

import android.util.Log;

import com.jproject.ytsmoviebrowser.contract.MoviesContract;
import com.jproject.ytsmoviebrowser.model.api.Client;
import com.jproject.ytsmoviebrowser.model.api.MoviesAPIService;
import com.jproject.ytsmoviebrowser.model.data.home.ResObj;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MoviesPresenter implements MoviesContract.Calls {

    int page = 1;
    private String TAG = "Details Presenter";
    private MoviesContract.View view;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MoviesPresenter(MoviesContract.View view) {
        this.view = view;
    }

    //CALLS
    /**********************************************************************************************/
    @Override
    public void getMoviesBySection(String sort, String genre) {
        disposable.add(getMoviesBySectionObservable(sort, genre).subscribeWith(getMoviesBySectionObserver()));
    }

    @Override
    public void getNextPageBySection(String sort, String genre) {
        ++page;
        disposable.add(getNextPageBySectionObservable(sort, genre).subscribeWith(getNextPageBySectionObserver()));
    }
    /**********************************************************************************************/
    //CALLS


    //OBSERVABLES
    /**********************************************************************************************/
    public Observable<ResObj> getMoviesBySectionObservable(String sort, String genre) {
        return Client.getRetrofit().create(MoviesAPIService.class)
                .getBySection(sort, genre, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getNextPageBySectionObservable(String sort, String genre) {
        return Client.getRetrofit().create(MoviesAPIService.class)
                .getNextPageBySection(sort, genre, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**********************************************************************************************/
    //OBSERVABLES


    //OBSERVERS
    /**********************************************************************************************/
    public DisposableObserver<ResObj> getMoviesBySectionObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showMoviesBySection(resObj);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
            }
        };
    }

    public DisposableObserver<ResObj> getNextPageBySectionObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showNextPageBySection(resObj);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
            }
        };
    }
    /**********************************************************************************************/
    //OBSERVERS


}
