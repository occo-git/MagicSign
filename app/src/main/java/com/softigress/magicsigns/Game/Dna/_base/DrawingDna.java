package com.softigress.magicsigns.Game.Dna._base;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.SystemClock;
import androidx.annotation.Keep;

import com.softigress.magicsigns._Base.ArrayRecyclableSimple;
import com.softigress.magicsigns._Base._Drawing._base.DrawingSimple;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawingTouchable;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchableListener;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.TaskUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class DrawingDna extends DrawingSimple implements IDrawingTouchable {

    private static final long showDuration = 1000;
    private static final long hideDuration = 1000;

    private long podDuration = 7000;
    private boolean isVisible = false;
    private long wavesTicks = 0;

    //private boolean isHorizontal = true;
    protected int podsCount = 1;
    private float podFl = .1f;
    private ArrayRecyclableSimple<DrawingDnaPod> pods = new ArrayRecyclableSimple<>(DrawingDnaPod.class);
    private DrawingDnaPod[] podItems;
    private DrawingDnaPod firstPod = null;
    private float fl = .5f;

    public boolean isHelpItem = false;

    protected DrawingDna(float fr, int podsCount, float podFr, float podFl) {
        super(fr);
        this.podsCount = podsCount;
        this.podFl = podFl;
        for (int i = 0; i < podsCount; i++) {
            DrawingDnaPod p = new DrawingDnaPod(podFr, podFl);
            pods.add(p);
            if (i == 0)
                firstPod = p;
        }
        podItems = pods.getItems();
    }

    public void setPodDuration(long podDuration) {
        this.podDuration = podDuration;
        if (podItems != null)
            for (DrawingDnaPod p : podItems)
                p.duration = podDuration;
    }

    private float waveCount = 1f;
    public void startWaves(float waveCount) {
        this.waveCount = waveCount;
        this.fl = .75f * waveCount * fr / MetrixUtils.screen_K;
        long cellOffset = podDuration + (long)(Utils.getRandom() * podDuration);
        long offsetDelta = (long)(waveCount * (podDuration / podsCount));
        int i = 0;
        if (podItems != null)
            for (DrawingDnaPod p : podItems) {
                //p.isHorisontal = isHorizontal;
                p.setOffset(cellOffset - offsetDelta * i++);
            }
        wavesTicks = 0;
        calcBounds();
    }

    private RectF bounds;
    private void calcBounds() {
        calc();
        //if (isHorizontal)
            bounds = new RectF(fx - fl / 2f, fy - podFl, fx + fl / 2f, fy + podFl);
        //else
        //    bounds = new RectF(fx - podFl, fy - fl / 2f, fx + podFl, fy + fl / 2f);
    }

    @Override
    public void setPoint(float fx, float fy) {
        super.setPoint(fx, fy);
        calcBounds();
    }

    //region alpha/color
    @Keep
    @Override
    public void setAlpha(int a) {
        super.setAlpha(a);
        if (podItems != null)
            for (DrawingDnaPod p : podItems)
                p.setAlpha(a);
    }

    public void setPodsOff() {
        if (podItems != null)
            for (DrawingDnaPod p : podItems)
                p.setStatusOff();
    }
    public DrawingDnaPod setPodsOn(int count) {
        int i = 0;
        DrawingDnaPod lastPodOn = null;
        if (podItems != null)
            for (DrawingDnaPod p : podItems) {
                if (i < count) {
                    p.setStatusOn();
                    lastPodOn = p;
                }  else
                    break;
                i++;
            }
        return lastPodOn;
    }
    protected DrawingDnaPod setPodsReady(int count) {
        int i = 0;
        DrawingDnaPod lastReadyPod = null;
        if (podItems != null)
            for (DrawingDnaPod p : podItems) {
                if (i < count) {
                    p.setStatusReady();
                    lastReadyPod = p;
                } else
                    break; //p.setStatusOff();
                i++;
            }
        if (!isHelpItem) { // для dna в игре храним значение x-координаты последнего готового dna pod
            if (lastReadyPod != null)
                CurrentSettings.dnaReadyFx = lastReadyPod.getFx();
            else
                CurrentSettings.dnaReadyFx = firstPod != null ? firstPod.getFx() : 0;//fx - waveCount * fr / 2f;
        }
        return lastReadyPod;
    }
    //endregion

    //region show / hide
    public void show() {
        if (!isHelpItem) {
            setAlpha(0);
            isVisible = true;
            ObjectAnimator.ofInt(this, "alpha", 0, 255).setDuration(showDuration).start();
        } else
            isVisible = true;
    }
    public void hide() {
        if (!isHelpItem) {
            ObjectAnimator.ofInt(this, "alpha", 255, 0).setDuration(hideDuration).start();
            TaskUtils.postDelayed(hideDuration, new Runnable() {
                @Override
                public void run() {
                    setAlpha(0);
                    isVisible = false;
                }
            });
        } else
            isVisible = false;
    }
    //endregion

    //region Touch
    private ITouchableListener touchListener;
    public void setListener(ITouchableListener l) {
        this.touchListener = l;
    }

    public boolean onTouch(int x, int y) {
        if (isVisible) {
            if (inBounds(x, y)) {
                if (touchListener != null)
                    touchListener.handelOnTouch(this);
                return true;
            }
        }
        return false;
    }

    public boolean onTouchUp(int x, int y) {
        if (isVisible) {
            if (inBounds(x, y)) {
                if (touchListener != null)
                    touchListener.handelOnTouchUp(this);
                return true;
            }
        }
        return false;
    }

    private boolean inBounds(float px, float py) {
        if (bounds != null) {
            float x0 = bounds.left * MetrixUtils.screen_metrix_width;
            float y0 = bounds.top * MetrixUtils.screen_metrix_height;
            float x1 = bounds.right * MetrixUtils.screen_metrix_width;
            float y1 = bounds.bottom * MetrixUtils.screen_metrix_height;
            return x0 < px && px < x1 && y0 < py && py < y1;
        } else
            return false;
    }

    public void onMoveTo(int x, int y) { }
    //endregion

    //region Draw
    @Override
    public void calc() {
        super.calc();
        //if (isHorizontal) {
            float pdx = fl / podsCount;
            float pfx = fx - fl / 2f;
            if (podItems != null)
                for (DrawingDnaPod p : podItems) {
                    p.setPoint(pfx, fy);
                    pfx += pdx;
                }
        /*} else {
            float pdy = fl / podsCount;
            float pfy = fy + fl / 2f;
            if (podItems != null)
                for (DrawingDnaPod p : podItems) {
                    p.setPoint(fx, pfy);
                    pfy -= pdy;
                }
        }*/
    }

    @Override
    public void drawFrame(Canvas c) {
        if (isVisible) {
            super.drawFrame(c);

            long ticks = SystemClock.elapsedRealtime();
            if (wavesTicks == 0)
                wavesTicks = ticks;
            long delta = ticks - wavesTicks;

            if (podItems != null)
                for (DrawingDnaPod p : podItems) {
                    p.nextFrame(delta);
                    p.drawFrame(c);
                }
        }
    }
    //endregion

    @Override
    public void recycle() {
        super.recycle();

        if (pods != null)
            pods.recycle();

        pods = null;
        bounds = null;
    }
}
