package com.jproject.ytsmoviebrowser.model.api;

import com.jproject.ytsmoviebrowser.YTSMovieBrowser;
import com.jproject.ytsmoviebrowser.model.data.home.ResObj;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesAPIService {

    @GET(YTSMovieBrowser.DEFAULT_HOME)
    Observable<ResObj>
    getBySection(@Query(value = "sort_by") String sort, @Query(value = "genre") String genre, @Query(value = "page") int page);

    @GET(YTSMovieBrowser.DEFAULT_HOME)
    Observable<ResObj>
    getNextPageBySection(@Query(value = "sort_by") String sort, @Query(value = "genre") String genre, @Query(value = "page") int page);
}
