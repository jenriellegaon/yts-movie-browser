package com.jproject.ytsmoviebrowser;

import android.app.Application;

public class YTSMovieBrowser extends Application {

    public static final String BASE_URL = "https://yts.am";
    public static final String DEFAULT_DETAILS = "/api/v2/movie_details.json?&with_cast=true&with_images=true";
    public static final String DEFAULT_HOME = "/api/v2/list_movies.json?";
    public static final String DEFAULT_HOME_LIMITED = "/api/v2/list_movies.json?&limit=5";

    public static final String TORRENT_DOWNLOAD_PATH = "/Downloads/YTS Movie Browser/Torrents";

}
