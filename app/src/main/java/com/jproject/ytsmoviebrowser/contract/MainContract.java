package com.jproject.ytsmoviebrowser.contract;

import com.jproject.ytsmoviebrowser.model.data.ResObj;

public interface MainContract {

    interface View {
        void showToast(String message);
        void showError(String error);

        void showTopDownloads(ResObj resObj);
        void showTopRated(ResObj resObj);
        void showLatestUploads(ResObj resObj);
        void showThisYear(ResObj resObj);
        void showNextPage(ResObj resObj);
        void initViews();
    }

    interface Calls {
        void getTopDownloads(String topDownloads);
        void getTopRated(String topRated);
        void getLatestUploads(String latestUploads);
        void getThisYear(String thisYear);
        void getNextPage(String sort);

        void detachAll();
    }

    interface CardClickListener {
        void onClick(android.view.View view, int position);
        void onLongClick(android.view.View view, int position);
    }

    interface OnBottomReachedListener {
        void onBottomReached(int position);
    }


}