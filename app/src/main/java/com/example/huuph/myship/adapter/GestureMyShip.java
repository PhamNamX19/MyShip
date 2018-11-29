package com.example.huuph.myship.adapter;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
public class GestureMyShip extends GestureDetector.SimpleOnGestureListener {
    private int SWIPE_THRESHOLD = 100;
    private int SWIPE_VELOCITY_THRESHOLD = 100;

    public GestureMyShip() {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //check vuot xuong
        if (e2.getY() - e1.getY() > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD ){
            Log.d("TAGGES","tren xuong duoi");
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
