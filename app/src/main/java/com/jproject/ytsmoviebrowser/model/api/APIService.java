package com.jproject.ytsmoviebrowser.model.api;

import com.jproject.ytsmoviebrowser.model.data.ResObj;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("/api/v2/list_movies.json?")
    Observable<ResObj> getPopularDownloads(@Query(value = "sort_by") String popularDownloads);

    @GET("/api/v2/list_movies.json?")
    Observable<ResObj> getTopRated(@Query(value = "sort_by") String topRated);

    @GET("/api/v2/list_movies.json?")
    Observable<ResObj> getLatestUploads(@Query(value = "sort_by") String latestUploads);

    @GET("/api/v2/list_movies.json?")
    Observable<ResObj> getThisYear(@Query(value = "sort_by") String thisYear);

}
