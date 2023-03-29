package com.softigress.magicsigns.UI._base.Effects.Circles;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import androidx.annotation.Keep;

import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;

public class CirclesBase implements IDrawing{

    private int actionDuration = 1000;
    private long startTicks = 0;
    private final float PI_actionTime;
    private float fr;
    private float r;
    private final float fRmin;
    private final float fRmax;
    private final float dr;
    private Paint paint0;
    private float fx, fy;
    private float x, y;
    private static final int aMax0 = 64;

    private boolean isStarted = false;
    private boolean isFinished = false;
    private boolean isOneTime = true;
    private boolean isReverse = false;

    @Keep
    public void setFy(float fy) { this.fy = fy; }

    public CirclesBase(float rMin, float rMax, int actionDuration) {
        fRmin = rMin;
        fRmax = rMax;
        dr = fRmax - fRmin;
        this.actionDuration = actionDuration;
        PI_actionTime = (float) (Math.PI / actionDuration);
        paint0 = PaintUtils.getPaintStrokeWhite(0, PaintUtils.strokeWidth2);
        isFinished = true;
    }

    public CirclesBase(float rMin, float rMax, int actionDuration, boolean isOneTime, boolean isReverse) {
        fRmin = rMin;
        fRmax = rMax;
        dr = fRmax - fRmin;
        this.actionDuration = actionDuration;
        PI_actionTime = (float)(Math.PI / actionDuration);
        paint0 = PaintUtils.getPaintStrokeWhite(0, PaintUtils.strokeWidth);
        this.isOneTime = isOneTime;
        this.isReverse = isReverse;
        isFinished = true;
    }

    public void setPoint(float fx, float fy) {
        this.fx = fx;
        this.fy = fy;
    }

    public void reset() {
        isStarted = false;
        startTicks = 0;
        isFinished = true;
    }

    public void start() {
        if (!isStarted) {
            isStarted = true;
            startTicks = 0;
            isFinished = false;
            if (isOneTime)
                fr = isReverse ? fRmax : fRmin;
        }
    }

    public void stop() { isStarted = false; }
    public void finish() { isFinished = true; }

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    @Override
    public void calc() {
        r = MetrixUtils.screen_metrix_height * fr;
        x = MetrixUtils.screen_metrix_width * fx;
        y = MetrixUtils.screen_metrix_height * fy;
        if (paint0 != null)
            paint0.setStrokeWidth(PaintUtils.strokeWidth2);
    }

    private void nextFrame() {
        if (isReverse) {
            if (fr >= fRmin)
                nextFrame(fRmax, -1);
            else
                fr = fRmin;
        }
        else { // !isReverse
            if (fr <= fRmax)
                nextFrame(fRmin, 1);
            else
                fr = fRmax;
        }
    }

    private void nextFrame(float RR, int reverseK) {
        long ticks = SystemClock.elapsedRealtime();
        if (startTicks == 0)
            startTicks = ticks;
        long delta = ticks - startTicks;
        if (delta > this.actionDuration) {
            if (isStarted)
                isFinished = isOneTime;
            else
                isFinished = true;

            if (isFinished)
                isStarted = false;
            startTicks = ticks;
            fr = RR;
        }
        this.fr = RR + (float) (reverseK * dr * Math.abs(Math.sin(0.5f * PI_actionTime * delta)));
        double ka = Math.abs(Math.sin(PI_actionTime * delta));
        paint0.setAlpha((int) (aMax0 * ka));
    }

    @Override
    public void drawFrame(Canvas canvas) {
        if (!isFinished) {
            nextFrame();
            calc();
            canvas.drawCircle(x, y, r, paint0);
        }
    }
    //endregion

    public void recycle() {
        paint0 = null;
    }
}
