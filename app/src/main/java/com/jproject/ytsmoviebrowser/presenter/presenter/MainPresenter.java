package com.jproject.ytsmoviebrowser.presenter.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jproject.ytsmoviebrowser.contract.MainContract;
import com.jproject.ytsmoviebrowser.model.api.APIService;
import com.jproject.ytsmoviebrowser.model.api.Client;
import com.jproject.ytsmoviebrowser.model.data.ResObj;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Calls {

    private String TAG = "Main Presenter";
    private MainContract.View view;
    int page = 1;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    //CALLS

    /**********************************************************************************************/
    @SuppressLint("CheckResult")
    @Override
    public void getPopularDownloads(String popularDownloads) {
        getPopularDownloadsObservable(popularDownloads).subscribeWith(getPopularDownloadsObserver());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getTopRated(String topRated) {
        getTopRatedObservable(topRated).subscribeWith(getTopRatedObserver());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getLatestUploads(String latestUploads) {
        getLatestUploadsObservable(latestUploads).subscribeWith(getLatestUploadsObserver());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getThisYear(String thisYear) {
        getThisYearObservable(thisYear).subscribeWith(getThisYearObserver());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getNextPage(String sort) {
        getNextPageObservable(sort).subscribeWith(getNextPageObserver());

    }
    /**********************************************************************************************/
    //CALLS


    //OBSERVABLES

    /**********************************************************************************************/
    public Observable<ResObj> getPopularDownloadsObservable(String popularDownloads) {
        return Client.getRetrofit().create(APIService.class)
                .getPopularDownloads(popularDownloads)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getTopRatedObservable(String topRated) {
        return Client.getRetrofit().create(APIService.class)
                .getTopRated(topRated)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getLatestUploadsObservable(String latestUploads) {
        return Client.getRetrofit().create(APIService.class)
                .getLatestUploads(latestUploads)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getThisYearObservable(String thisYear) {
        return Client.getRetrofit().create(APIService.class)
                .getThisYear(thisYear)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getNextPageObservable(String sort) {

        //Increment page number
        ++page;
        return Client.getRetrofit().create(APIService.class)
                .getNextPage(sort, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**********************************************************************************************/
    //OBSERVABLES


    //OBSERVERS

    /**********************************************************************************************/

    public DisposableObserver<ResObj> getPopularDownloadsObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showPopularDownloads(resObj);
                dispose();
                getPopularDownloadsObserver().dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                dispose();
                getPopularDownloadsObserver().dispose();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                dispose();
                getPopularDownloadsObserver().dispose();
            }
        };
    }

    public DisposableObserver<ResObj> getTopRatedObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showTopRated(resObj);
                dispose();
                getTopRatedObserver().dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                dispose();
                getTopRatedObserver().dispose();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                dispose();
                getTopRatedObserver().dispose();
            }
        };
    }

    public DisposableObserver<ResObj> getLatestUploadsObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showLatestUploads(resObj);
                dispose();
                getLatestUploadsObserver().dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                dispose();
                getLatestUploadsObserver().dispose();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                dispose();
                getLatestUploadsObserver().dispose();
            }
        };
    }

    public DisposableObserver<ResObj> getThisYearObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showThisYear(resObj);
                dispose();
                getThisYearObserver().dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                dispose();
                getThisYearObserver().dispose();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                dispose();
                getThisYearObserver().dispose();
            }
        };
    }


    public DisposableObserver<ResObj> getNextPageObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showNextPage(resObj);
                dispose();
                getNextPageObserver().dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                dispose();
                getNextPageObserver().dispose();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                dispose();
                getNextPageObserver().dispose();
            }
        };
    }

    /**********************************************************************************************/
    //OBSERVERS
}
