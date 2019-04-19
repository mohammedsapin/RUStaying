package com.example.rustaying;

import android.support.v7.widget.RecyclerView;

public interface SwipeControllerListener {
    void onSwipe(RecyclerView.ViewHolder viewHolder,int direction, int position);
}
