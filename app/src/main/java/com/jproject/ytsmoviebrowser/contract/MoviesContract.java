package com.jproject.ytsmoviebrowser.contract;

import com.jproject.ytsmoviebrowser.model.data.home.ResObj;


public interface MoviesContract {

    interface View {
        void initViews();

        void showToast(String message);

        void showError(String error);

        void showMoviesBySection(ResObj resObj);

        void showNextPageBySection(ResObj resObj);

    }

    interface Calls {
        void getMoviesBySection(String section);

        void getNextPageBySection(String sort);
    }

    interface OnBottomReachedListener {
        void onBottomReached(int position);
    }

    interface MoviesCardClickListener {
        void onClick(android.view.View view, int pos);

        void onLongClick(android.view.View view, int pos);
    }
}
