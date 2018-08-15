package com.jproject.ytsmoviebrowser.contract;

import com.jproject.ytsmoviebrowser.model.data.list.ResObj;

public interface MainContract {

    interface View {
        void showError(String error);
        void showTopDownloads(ResObj resObj);
        void showTopRated(ResObj resObj);
        void showLatestUploads(ResObj resObj);
        void showThisYear(ResObj resObj);
        void initViews();
    }

    interface Calls {
        void getTopDownloads(String topDownloads);
        void getTopRated(String topRated);
        void getLatestUploads(String latestUploads);
        void getThisYear(String thisYear);
        void detachAll();
    }
}