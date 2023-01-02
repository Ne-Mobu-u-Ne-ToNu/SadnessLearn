package com.example.sadnesslearn.classes;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.sadnesslearn.R;

public class AnimationHelper {
    public static void buttonAnimation(View v, Context context) {
        Animation scaleUp, scaleDown;
        scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(context, R.anim.scale_down);

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        v.startAnimation(scaleUp);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        v.startAnimation(scaleDown);
                }
                return false;
            }
        });
    }
}
