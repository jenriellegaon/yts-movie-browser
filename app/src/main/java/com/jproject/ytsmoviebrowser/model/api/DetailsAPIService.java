package com.jproject.ytsmoviebrowser.model.api;

import com.jproject.ytsmoviebrowser.model.data.details.ResObj;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DetailsAPIService {

    @GET(Client.DEFAULT_DETAILS)
    Observable<ResObj>
    getMovieDetails(@Query(value = "movie_id") String movie_id);

}
