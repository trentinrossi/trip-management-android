package com.rodrigorossi.tripsfinalproject.util;

import android.content.Context;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodrigorossi.tripsfinalproject.R;

public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private RecyclerView recyclerView;
    private OnItemClickListener listenerOnItemClick;
    private GestureDetector gestureDetector;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onLongItemClick(View view, int position);
    }

    public RecyclerItemClickListener(Context context, RecyclerView recycler, OnItemClickListener listener) {
        recyclerView = recycler;
        listenerOnItemClick = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && listenerOnItemClick != null) {
                            listenerOnItemClick.onItemClick(child, recyclerView.getChildAdapterPosition(child));
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && listenerOnItemClick != null) {
                            listenerOnItemClick.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }
}
