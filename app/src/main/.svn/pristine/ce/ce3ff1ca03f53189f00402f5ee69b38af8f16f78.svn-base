package com.softigress.magicsigns.Story;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.SystemClock;
import com.softigress.magicsigns.UI._base.Effects.Trace.DrawingTrace;
import com.softigress.magicsigns._Base._Drawing.Bezier.DrawingBezierPath;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._system.Utils.PaintUtils;

public class Comet extends DrawingBase {

    private boolean isStarted = false;

    private float fd0;
    private long duration = 2000;
    private long startTicks = 0;

    private DrawingBezierPath path;
    private DrawingTrace trace;
    private static final float traceFr = .005f;
    private static final int tracePointDuration = 2000;
    private static final float traceFluctuation = .025f;

    private int maxAlpha = 224; // 196
    private static final int maxPaintAlpha = 64;
    private Paint tracePaint;
    private Animator anim;

    public Comet(float fd, Bitmap bitmap) {
        super(fd);

        this.fd0 = fd;
        paint = PaintUtils.getPaintWhite(maxAlpha);
        tracePaint = PaintUtils.getPaintWhite(maxPaintAlpha);
        setDefaultBitmap(bitmap);
        if (tracePaint != null)
            trace = new DrawingTrace(traceFr, tracePointDuration, tracePaint);
        setAlpha(maxAlpha);
    }

    public void start(float fx0, float fy0, float fx1, float fy1, float fx2, float fy2, long duration) {
        setPoint(fx0, fy0);
        show();

        startTicks = 0;
        this.duration = duration;
        path = new DrawingBezierPath(fx0, fy0, fx1, fy1, fx2, fy2);
        path.start(duration);
        isStarted = true;
        show();
        anim = ObjectAnimator.ofFloat(this, "fd", 0, fd0).setDuration(duration);
        anim.start();
    }

    @Override
    public long hide() {
        if (anim != null)
            anim.cancel();
        isStarted = false;
        return super.hide();
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
        int a = maxAlpha * alpha / 255;
        int ap = maxPaintAlpha * alpha / 255;
        if (paint != null)
            paint.setAlpha(a);
        if (tracePaint != null)
            tracePaint.setAlpha(ap);
        if (trace != null)
            trace.setAlpha(ap);
    }

    //region Draw
    private void nextFrame(long ticks) {
        if (isStarted) {
            if (startTicks == 0)
                startTicks = ticks;
            if (path != null) {
                PointF point = path.getCurrentPoint(ticks);
                if (point != null) {
                    setPoint(point);
                    long delta = ticks - startTicks;
                    if (delta <= duration) {
                        float fluctuation = traceFluctuation * delta / duration;
                        if (trace != null)
                            trace.addPoint(point, fluctuation);
                    }
                }
            }
        }
    }

    @Override
    public void drawBackground(Canvas c) {
        super.drawBackground(c);

        if (isVisible()) {
            if (paint != null)
                c.drawCircle(x, y, .25f * h, paint);
        }
    }
    @Override
    public void drawFrame(Canvas c) {
        // точки трейса
        if (isVisible()) {
            if (trace != null)
                trace.drawFrame(c);
            nextFrame(SystemClock.elapsedRealtime());
            super.drawFrame(c);
        }
    }
    //endregion

    @Override
    public void recycle() {
        super.recycle();

        if (path != null)
            path.recycle();
        if (trace != null)
            trace.recycle();

        path = null;
        tracePaint = null;
        trace = null;
    }
}
