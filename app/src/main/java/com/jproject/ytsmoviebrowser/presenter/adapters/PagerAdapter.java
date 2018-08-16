package com.jproject.ytsmoviebrowser.presenter.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jproject.ytsmoviebrowser.view.fragments.CastTab;
import com.jproject.ytsmoviebrowser.view.fragments.InfoTab;
import com.jproject.ytsmoviebrowser.view.fragments.ReviewsTab;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new InfoTab();
            case 1:
                return new CastTab();
            case 2:
                return new ReviewsTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
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
}
