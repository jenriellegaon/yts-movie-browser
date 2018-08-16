package com.jproject.ytsmoviebrowser.contract;

import com.jproject.ytsmoviebrowser.model.data.home.ResObj;


public interface GenreContract {

    interface View {
        void initViews();

        void showToast(String message);

        void showError(String error);

        void showMoviesBySection(ResObj resObj);

        void showMoviesByGenre(ResObj resObj);

        void showNextPageBySection(ResObj resObj);

        void showNextPageByGenre(ResObj resObj);
    }

    interface Calls {
        void getMoviesBySection(String section);

        void getMoviesByGenre(String section, String genre);

        void getNextPageBySection(String sort);

        void getNextPageByGenre(String sort, String genre);
    }


    interface OnBottomReachedListener {
        void onBottomReached(int position);
    }
}
