package com.jproject.ytsmoviebrowser.contract;

import android.content.Context;

import com.jproject.ytsmoviebrowser.model.data.details.ResObj;

public interface DetailsContract {

    interface View {
        void initViews();

        void showMovieDetails(ResObj resObj);

        void showError(String error);

        void showToast(String message);

        void setImage(Context context, String imageurl);
    }

    interface Calls {
        void getMovieDetails(String movie_id);
    }

}
