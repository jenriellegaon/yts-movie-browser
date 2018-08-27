package com.jproject.ytsmoviebrowser.contract;

import com.jproject.ytsmoviebrowser.model.data.home.ResObj;

public interface HomeContract {

    interface View {
        void showError(String error);
        void showTopDownloads(ResObj resObj);
        void showTopRated(ResObj resObj);
        void showLatestUploads(ResObj resObj);
        void initViews();
    }

    interface Calls {
        void getTopDownloads(String topDownloads);
        void getTopRated(String topRated);
        void getLatestUploads(String latestUploads);
        void detachAll();
    }

    interface HomeCardClickListener {
        void onClick(android.view.View view, int position);

        void onLongClick(android.view.View view, int position);
    }
}