package com.softigress.magicsigns._system.Utils;

import android.animation.Animator;
import android.os.Handler;

public abstract class TaskUtils {

    public static void postDelayed(long delayTicks, final Runnable r) {
        if (delayTicks > 0) {
            final Handler h = new Handler();
            //h.postDelayed(r, delayTicks);
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        r.run();
                        h.removeCallbacksAndMessages(null);
                    } catch (Throwable t) {
                        Utils.CrashReport("TaskUtils.postDelayed.run", t);
                    }
                }
            }, delayTicks);
        } else {
            r.run();
        }
    }

    public static void onAnimEnd(Animator anim, final Runnable runnable) {
        anim.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) { }
            @Override public void onAnimationEnd(Animator animation) { runnable.run(); }
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        });
    }

    public static void onAnimStart(Animator anim, final Runnable runnable) {
        anim.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) { runnable.run(); }
            @Override public void onAnimationEnd(Animator animation) { }
            @Override public void onAnimationCancel(Animator animation) { }
            @Override public void onAnimationRepeat(Animator animation) { }
        });
    }
}