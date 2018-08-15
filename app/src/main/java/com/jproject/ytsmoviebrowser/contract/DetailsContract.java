package com.jproject.ytsmoviebrowser.contract;

import com.jproject.ytsmoviebrowser.model.data.details.ResObj;


public interface DetailsContract {

    interface View {
        void initViews();

        void showToast(String message);

        void showError(String error);

        void showMovieDetails(ResObj resObj);

        void showNextPage(ResObj resObj);
    }

    interface Calls {
        void showMoviesByGenre(String genre);

        void showNextPage(String sort, String genre);
    }

    interface CardClickListener {
        void onClick(android.view.View view, int position);
        void onLongClick(android.view.View view, int position);
    }

    interface OnBottomReachedListener {
        void onBottomReached(int position);
    }
}
