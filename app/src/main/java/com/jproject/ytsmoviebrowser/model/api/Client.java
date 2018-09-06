package com.jproject.ytsmoviebrowser.model.api;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static final String BASE_URL = "https://yts.am";
    public static final String DEFAULT_DETAILS = "/api/v2/movie_details.json?&with_cast=true&with_images=true";
    public static final String DEFAULT_HOME = "/api/v2/list_movies.json?";
    public static final String DEFAULT_HOME_LIMITED = "/api/v2/list_movies.json?&limit=5";

    public static Retrofit retrofit;

    public static Retrofit getRetrofit() {

        if (retrofit == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(0, 1, TimeUnit.NANOSECONDS));

            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;


    }
}
