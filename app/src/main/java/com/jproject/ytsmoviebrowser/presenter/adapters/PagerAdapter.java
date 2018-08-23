package com.jproject.ytsmoviebrowser.presenter.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jproject.ytsmoviebrowser.view.fragments.CastTab;
import com.jproject.ytsmoviebrowser.view.fragments.InfoTab;
import com.jproject.ytsmoviebrowser.view.fragments.ReviewsTab;

public class PagerAdapter extends FragmentPagerAdapter {

    private String id;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:

                InfoTab infoTab = new InfoTab();
                Bundle bundleInfo = new Bundle();
                bundleInfo.putString("movie_id", getId());
                infoTab.setArguments(bundleInfo);
                return infoTab;

            case 1:
                CastTab castTab = new CastTab();
                Bundle bundleCast = new Bundle();
                bundleCast.putString("movie_id", getId());
                castTab.setArguments(bundleCast);
                return castTab;

            case 2:

                ReviewsTab reviewsTab = new ReviewsTab();
                Bundle bundleReviews = new Bundle();
                bundleReviews.putString("movie_id", getId());
                reviewsTab.setArguments(bundleReviews);
                return reviewsTab;

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Info";
            case 1:
                return "Cast";

            case 2:
                return "Reviews";
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
