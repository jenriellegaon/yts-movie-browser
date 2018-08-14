package com.jproject.ytsmoviebrowser.model.data.list;

import java.util.List;

public class MovieDataModel {

    private String headerTitle;
    private List<Movie> movieList = null;

    public MovieDataModel() {
    }

    public MovieDataModel(String headerTitle, List<Movie> movieList) {
        this.headerTitle = headerTitle;
        this.movieList = movieList;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}