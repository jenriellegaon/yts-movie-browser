package com.jproject.ytsmoviebrowser.presenter.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jproject.ytsmoviebrowser.contract.HomeContract;
import com.jproject.ytsmoviebrowser.model.api.Client;
import com.jproject.ytsmoviebrowser.model.api.HomeAPIService;
import com.jproject.ytsmoviebrowser.model.data.home.ResObj;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Calls {

    private String TAG = "Main Presenter";
    private HomeContract.View view;
    private CompositeDisposable disposable = new CompositeDisposable();


    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    //CALLS
    /**********************************************************************************************/
    @SuppressLint("CheckResult")
    @Override
    public void getTopDownloads(String topDownloads) {
        disposable.add(getTopDownloadsObservable(topDownloads).subscribeWith(getTopDownloadsObserver()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getTopRated(String topRated) {
        disposable.add(getTopRatedObservable(topRated).subscribeWith(getTopRatedObserver()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getLatestUploads(String latestUploads) {
        disposable.add(getLatestUploadsObservable(latestUploads).subscribeWith(getLatestUploadsObserver()));
    }

    @Override
    public void detachAll() {
        disposable.clear();
        view = null;
    }

    /**********************************************************************************************/
    //CALLS


    //OBSERVABLES
    /**********************************************************************************************/
    public Observable<ResObj> getTopDownloadsObservable(String topDownloads) {
        return Client.getRetrofit().create(HomeAPIService.class)
                .getTopDownloads(topDownloads)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getTopRatedObservable(String topRated) {
        return Client.getRetrofit().create(HomeAPIService.class)
                .getTopRated(topRated)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getLatestUploadsObservable(String latestUploads) {
        return Client.getRetrofit().create(HomeAPIService.class)
                .getLatestUploads(latestUploads)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**********************************************************************************************/
    //OBSERVABLES


    //OBSERVERS
    /**********************************************************************************************/
    public DisposableObserver<ResObj> getTopDownloadsObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showTopDownloads(resObj);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                view = null;
                detachAll();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
            }
        };
    }

    public DisposableObserver<ResObj> getTopRatedObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showTopRated(resObj);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                view = null;
                detachAll();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
            }
        };
    }

    public DisposableObserver<ResObj> getLatestUploadsObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showLatestUploads(resObj);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                view = null;
                detachAll();
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