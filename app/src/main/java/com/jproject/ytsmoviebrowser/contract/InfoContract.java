package com.jproject.ytsmoviebrowser.contract;

import com.jproject.ytsmoviebrowser.model.data.details.ResObj;

public interface InfoContract {

    interface View {

        void showInfo(ResObj resObj);

        void showError(String error);

        void showToast(String message);
    }

    interface Calls {

        void getInfo(String movie_id);
    }
}
