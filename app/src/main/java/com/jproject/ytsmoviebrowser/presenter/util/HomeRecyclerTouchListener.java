package com.jproject.ytsmoviebrowser.presenter.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.jproject.ytsmoviebrowser.contract.HomeContract;

public class HomeRecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private HomeContract.HomeCardClickListener homeCardClickListener;

    public HomeRecyclerTouchListener(Context context, final RecyclerView recyclerView, final HomeContract.HomeCardClickListener homeCardClickListener) {
        this.homeCardClickListener = homeCardClickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && homeCardClickListener != null) {
                    homeCardClickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    //rv.getChildPosition is deprecated
                }
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && homeCardClickListener != null && gestureDetector.onTouchEvent(e)) {
            homeCardClickListener.onClick(child, rv.getChildAdapterPosition(child));
            //rv.getChildPosition is deprecated
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}