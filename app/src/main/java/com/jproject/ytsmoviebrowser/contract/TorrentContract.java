package com.jproject.ytsmoviebrowser.contract;

import android.content.Context;

import com.jproject.ytsmoviebrowser.model.data.details.ResObj;

public interface TorrentContract {

    interface View {
        void initViews();

        void showTorrentDetails(ResObj resObj);

        void showError(String error);

        void showToast(String message);

        void setImage(Context context, String imageurl);
    }

    interface Calls {
        void getTorrentDetails(String movie_id);
    }

}
