package com.jproject.ytsmoviebrowser.presenter.presenter;

import android.util.Log;

import com.jproject.ytsmoviebrowser.contract.GenreContract;
import com.jproject.ytsmoviebrowser.model.api.APIService;
import com.jproject.ytsmoviebrowser.model.api.Client;
import com.jproject.ytsmoviebrowser.model.data.home.ResObj;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GenrePresenter implements GenreContract.Calls {

    int page = 1;
    private String TAG = "Details Presenter";
    private GenreContract.View view;
    private CompositeDisposable disposable = new CompositeDisposable();

    public GenrePresenter(GenreContract.View view) {
        this.view = view;
    }

    //CALLS
    /**********************************************************************************************/
    @Override
    public void getMoviesBySection(String section) {
        disposable.add(getMoviesBySectionObservable(section).subscribeWith(getMoviesBySectionObserver()));
    }

    @Override
    public void getMoviesByGenre(String section, String genre) {
        disposable.add(getMoviesByGenreObservable(section, genre).subscribeWith(getMoviesByGenreObserver()));
    }

    @Override
    public void getNextPageBySection(String sort) {
        ++page;
        disposable.add(getNextPageBySectionObservable(sort).subscribeWith(getNextPageBySectionObserver()));
    }

    @Override
    public void getNextPageByGenre(String sort, String genre) {
        ++page;
        disposable.add(getNextPageByGenreObservable(sort, genre).subscribeWith(getNextPageByGenreObserver()));
    }
    /**********************************************************************************************/
    //CALLS


    //OBSERVABLES
    /**********************************************************************************************/
    public Observable<ResObj> getMoviesBySectionObservable(String sort) {
        return Client.getRetrofit().create(APIService.class)
                .getBySection(sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getMoviesByGenreObservable(String sort, String genre) {
        return Client.getRetrofit().create(APIService.class)
                .getByGenre(sort, genre)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getNextPageBySectionObservable(String sort) {
        return Client.getRetrofit().create(APIService.class)
                .getNextPageBySection(sort, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<ResObj> getNextPageByGenreObservable(String sort, String genre) {
        return Client.getRetrofit().create(APIService.class)
                .getNextPageByGenre(sort, genre, page)
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

    public DisposableObserver<ResObj> getMoviesByGenreObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showMoviesByGenre(resObj);
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

    public DisposableObserver<ResObj> getNextPageByGenreObserver() {
        return new DisposableObserver<ResObj>() {

            @Override
            public void onNext(@NonNull ResObj resObj) {
                view.showNextPageByGenre(resObj);
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
