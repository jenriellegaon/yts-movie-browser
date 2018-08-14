package com.jproject.ytsmoviebrowser.model.api;

import com.jproject.ytsmoviebrowser.model.data.list.ResObj;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET(Client.DEFAULT_LIMITED)
    Observable<ResObj> getTopDownloads(@Query(value = "sort_by") String topDownloads);

    @GET(Client.DEFAULT_LIMITED)
    Observable<ResObj> getTopRated(@Query(value = "sort_by") String topRated);

    @GET(Client.DEFAULT_LIMITED)
    Observable<ResObj> getLatestUploads(@Query(value = "sort_by") String latestUploads);

    @GET(Client.DEFAULT_LIMITED)
    Observable<ResObj> getThisYear(@Query(value = "sort_by") String thisYear);

    @GET(Client.DEFAULT)
    Observable<ResObj> getNextPage(@Query(value = "sort_by") String sort, @Query(value = "page") int page);

}
