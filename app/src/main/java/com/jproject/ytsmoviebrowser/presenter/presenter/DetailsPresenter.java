package com.jproject.ytsmoviebrowser.presenter.presenter;

import android.util.Log;

import com.jproject.ytsmoviebrowser.contract.DetailsContract;
import com.jproject.ytsmoviebrowser.model.api.APIService;
import com.jproject.ytsmoviebrowser.model.api.Client;
import com.jproject.ytsmoviebrowser.model.data.details.ResObj;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailsPresenter implements DetailsContract.Calls {

    int page = 1;
    private String TAG = "Details Presenter";
    private DetailsContract.View view;
    private CompositeDisposable disposable = new CompositeDisposable();

    public DetailsPresenter(DetailsContract.View view) {
        this.view = view;
    }

    //CALLS

    /**********************************************************************************************/
    @Override
    public void showMoviesByGenre(String genre) {

    }

    @Override
    public void showNextPage(String sort, String genre) {
        ++page;

    }
    /**********************************************************************************************/
    //CALLS


    //OBSERVABLES

    /**********************************************************************************************/
    public Observable<ResObj> getMoviesByGenreObservable(String sort, String genre) {
        return Client.getRetrofit().create(APIService.class)
                .getByGenre(sort, genre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getNextPageObservable(String sort, String genre) {
        return Client.getRetrofit().create(APIService.class)
                .getNextPage(sort, genre, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**********************************************************************************************/
    //OBSERVABLES


    //OBSERVERS

    /**********************************************************************************************/
    public DisposableObserver<ResObj> getMoviesByGenreObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showMovieDetails(resObj);
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
