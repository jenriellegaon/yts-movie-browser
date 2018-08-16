package com.jproject.ytsmoviebrowser.model.api;

import com.jproject.ytsmoviebrowser.model.data.home.ResObj;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeAPIService {

    @GET(Client.DEFAULT_HOME_LIMITED)
    Observable<ResObj>
    getTopDownloads(@Query(value = "sort_by") String topDownloads);

    @GET(Client.DEFAULT_HOME_LIMITED)
    Observable<ResObj>
    getTopRated(@Query(value = "sort_by") String topRated);

    @GET(Client.DEFAULT_HOME_LIMITED)
    Observable<ResObj>
    getLatestUploads(@Query(value = "sort_by") String latestUploads);

}
