package com.jproject.ytsmoviebrowser.model.api;

import com.jproject.ytsmoviebrowser.YTSMovieBrowser;
import com.jproject.ytsmoviebrowser.model.data.home.ResObj;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeAPIService {

    @GET(YTSMovieBrowser.DEFAULT_HOME_LIMITED)
    Observable<ResObj>
    getTopDownloads(@Query(value = "sort_by") String topDownloads);

    @GET(YTSMovieBrowser.DEFAULT_HOME_LIMITED)
    Observable<ResObj>
    getTopRated(@Query(value = "sort_by") String topRated);

    @GET(YTSMovieBrowser.DEFAULT_HOME_LIMITED)
    Observable<ResObj>
    getLatestUploads(@Query(value = "sort_by") String latestUploads);

}
