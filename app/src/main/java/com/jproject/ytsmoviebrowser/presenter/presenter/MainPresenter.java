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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Calls {

    private String TAG = "Main Presenter";
    private MainContract.View view;
    private CompositeDisposable disposable = new CompositeDisposable();

    int page = 1;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    //CALLS
    /**********************************************************************************************/
    @SuppressLint("CheckResult")
    @Override
    public void getTopDownloads(String topDownloads) {
//        getPopularDownloadsObservable(popularDownloads).subscribeWith(getPopularDownloadsObserver());

        disposable.add(getTopDownloadsObservable(topDownloads).subscribeWith(getTopDownloadsObserver()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getTopRated(String topRated) {
//        getTopRatedObservable(topRated).subscribeWith(getTopRatedObserver());

        disposable.add(getTopRatedObservable(topRated).subscribeWith(getTopRatedObserver()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getLatestUploads(String latestUploads) {
//        getLatestUploadsObservable(latestUploads).subscribeWith(getLatestUploadsObserver());
        disposable.add(getLatestUploadsObservable(latestUploads).subscribeWith(getLatestUploadsObserver()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getThisYear(String thisYear) {
//        getThisYearObservable(thisYear).subscribeWith(getThisYearObserver());
        disposable.add(getThisYearObservable(thisYear).subscribeWith(getThisYearObserver()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getNextPage(String sort) {
        getNextPageObservable(sort).subscribeWith(getNextPageObserver());
    }

    @Override
    public void detachAll() {
        disposable.clear();
    }
    /**********************************************************************************************/
    //CALLS



    //OBSERVABLES
    /**********************************************************************************************/
    public Observable<ResObj> getTopDownloadsObservable(String topDownloads) {
        return Client.getRetrofit().create(APIService.class)
                .getTopDownloads(topDownloads)
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
                disposable.clear();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                disposable.clear();
                if (isDisposed()) {
                    Log.d("POPULAR OBSERVER", "DISPOSED");
                }

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
                disposable.clear();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                disposable.clear();
                if (isDisposed()) {
                    Log.d("TOP RATED OBSERVER", "DISPOSED");
                }
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
                disposable.clear();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                disposable.clear();
                if (isDisposed()) {
                    Log.d("LATEST UPLOADS OBSERVER", "DISPOSED");
                }
            }
        };
    }

    public DisposableObserver<ResObj> getThisYearObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showThisYear(resObj);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "Error" + e);
                e.printStackTrace();
                view.showError("Error Fetching Data");
                disposable.clear();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Completed");
                disposable.clear();
                if (isDisposed()) {
                    Log.d("THIS YEAR OBSERVER", "DISPOSED");
                }

            }
        };
    }


    public DisposableObserver<ResObj> getNextPageObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showNextPage(resObj);
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