package com.jproject.ytsmoviebrowser.model.data.single;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jproject.ytsmoviebrowser.model.data.list.Movie;

public class Data {

    @SerializedName("movie")
    @Expose
    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
