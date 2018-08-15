package com.jproject.ytsmoviebrowser.model.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET(Client.DEFAULT_LIMITED)
    Observable<com.jproject.ytsmoviebrowser.model.data.home.ResObj>
    getTopDownloads(@Query(value = "sort_by") String topDownloads);

    @GET(Client.DEFAULT_LIMITED)
    Observable<com.jproject.ytsmoviebrowser.model.data.home.ResObj>
    getTopRated(@Query(value = "sort_by") String topRated);

    @GET(Client.DEFAULT_LIMITED)
    Observable<com.jproject.ytsmoviebrowser.model.data.home.ResObj>
    getLatestUploads(@Query(value = "sort_by") String latestUploads);

    @GET(Client.DEFAULT_LIMITED)
    Observable<com.jproject.ytsmoviebrowser.model.data.home.ResObj>
    getThisYear(@Query(value = "sort_by") String thisYear);

    @GET(Client.DEFAULT)
    Observable<com.jproject.ytsmoviebrowser.model.data.details.ResObj>
    getByGenre(@Query(value = "sort_by") String sort, @Query(value = "genre") String genre);

    @GET(Client.DEFAULT)
    Observable<com.jproject.ytsmoviebrowser.model.data.details.ResObj>
    getNextPage(@Query(value = "sort_by") String sort, @Query(value = "genre") String genre, @Query(value = "page") int page);

}
