package com.jproject.ytsmoviebrowser.presenter.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.jproject.ytsmoviebrowser.contract.DetailsContract;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private DetailsContract.CardClickListener cardClickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final DetailsContract.CardClickListener cardClickListener) {
        this.cardClickListener = cardClickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && cardClickListener != null) {
                    cardClickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    //rv.getChildPosition is deprecated
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && cardClickListener != null && gestureDetector.onTouchEvent(e)) {
            cardClickListener.onClick(child, rv.getChildAdapterPosition(child));
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