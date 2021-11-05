package com.example.projectnotes.Utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class AnimUtil {
    public static void expand(final View v,final View v2,final View v3, int duration, int targetWidth) {

        int prevWidth  = v.getWidth();
        v.setVisibility(View.VISIBLE);
        v2.setAlpha(1f);
        v3.setAlpha(1f);
        if(prevWidth>0){
            v.getLayoutParams().width=0;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().width = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });

        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(1f,0.5f);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v2.setAlpha((float) animation.getAnimatedValue());
                v2.requestLayout();
                v3.setAlpha((float) animation.getAnimatedValue());
                v3.requestLayout();
            }
        });


        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();

        valueAnimator2.setInterpolator(new DecelerateInterpolator());
        valueAnimator2.setDuration(duration);
        valueAnimator2.start();
    }

    public static void collapse(final View v,final View v2,final View v3, int duration, int targetWidth) {
        int prevWidth  = v.getWidth();
        float prev = v2.getAlpha();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevWidth, targetWidth);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().width = (int) animation.getAnimatedValue();
                v.requestLayout();
                if(v.getLayoutParams().width==targetWidth){
                    v.setVisibility(View.GONE);
                }
            }
        });
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(prev,1f);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v2.setAlpha((float) animation.getAnimatedValue());
                v2.requestLayout();
                v3.setAlpha((float) animation.getAnimatedValue());
                v3.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();

        valueAnimator2.setInterpolator(new DecelerateInterpolator());
        valueAnimator2.setDuration(duration);
        valueAnimator2.start();
    }
}
