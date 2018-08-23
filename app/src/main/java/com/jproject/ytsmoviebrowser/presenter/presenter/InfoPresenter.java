package com.jproject.ytsmoviebrowser.presenter.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jproject.ytsmoviebrowser.contract.InfoContract;
import com.jproject.ytsmoviebrowser.model.api.Client;
import com.jproject.ytsmoviebrowser.model.api.DetailsAPIService;
import com.jproject.ytsmoviebrowser.model.data.details.ResObj;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class InfoPresenter implements InfoContract.Calls {

    private String TAG = "Info Presenter";
    private InfoContract.View view;
    private CompositeDisposable disposable = new CompositeDisposable();

    public InfoPresenter(InfoContract.View view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getInfo(String movie_id) {
        disposable.add(getInfoObservable(movie_id).subscribeWith(getInfoObserver()));
    }

    //OBSERVABLES

    /**********************************************************************************************/
    public Observable<ResObj> getInfoObservable(String movie_id) {
        return Client.getRetrofit().create(DetailsAPIService.class)
                .getMovieDetails(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**********************************************************************************************/
    //OBSERVABLES


    //OBSERVERS

    /**********************************************************************************************/
    public DisposableObserver<ResObj> getInfoObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showInfo(resObj);
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
