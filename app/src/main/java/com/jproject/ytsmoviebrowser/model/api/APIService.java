package com.jproject.ytsmoviebrowser.model.api;

import com.jproject.ytsmoviebrowser.model.data.ResObj;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET(Client.DEFAULT)
    Observable<ResObj> getPopularDownloads(@Query(value = "sort_by") String popularDownloads);

    @GET(Client.DEFAULT)
    Observable<ResObj> getTopRated(@Query(value = "sort_by") String topRated);

    @GET(Client.DEFAULT)
    Observable<ResObj> getLatestUploads(@Query(value = "sort_by") String latestUploads);

    @GET(Client.DEFAULT)
    Observable<ResObj> getThisYear(@Query(value = "sort_by") String thisYear);

    @GET(Client.DEFAULT)
    Observable<ResObj> getNextPage(@Query(value = "sort_by") String sort, @Query(value = "page") int page);

}
