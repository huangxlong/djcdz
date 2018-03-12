package com.djc.djcdz.view.tickview;

/**
 * Created by Administrator
 * on 2018/3/5 星期一.
 */

public interface TickAnimatorListener {
    void onAnimationStart(TickView tickView);

    void onAnimationEnd(TickView tickView);

    abstract class TickAnimatorListenerAdapter implements TickAnimatorListener {
        @Override
        public void onAnimationStart(TickView tickView) {

        }

        @Override
        public void onAnimationEnd(TickView tickView) {

        }
    }
}