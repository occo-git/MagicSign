package com.softigress.magicsigns.UI._base.Effects.BackStars;

import com.softigress.magicsigns._Base.IRecycle;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class BackStar implements IRecycle {

    private boolean isVisible = false;
    private boolean isStop = false;

    private int w; // ширина области появления звезды
    private int h; // высота области появления звезды
    int x;
    int y; // x, y - координаты звезды
    private int y0; // y0 - начальная координата движения звезды
    int r;
    private final float maxR;
    private int maxAlpha;
    private int a;
    private float k;

    private final float PI_restoreTime;
    private int offsetTicks;
    private final static int restoreTime = 5000;
    public int moveDuration = 20000; // время перемещения по высоте this.h
    private float moveK = 1f; // коэффициент для корректировки скорости перемещения

    private boolean isBackStar = false;
    private boolean isRandomY0 = false;

    public BackStar(int w, int h, float maxR) {
        isBackStar = true;
        this.w = w;
        this.h = h;
        this.maxR = maxR;
        PI_restoreTime = (float) (Math.PI / restoreTime);
    }

    private float fx, fy, fw, fh;
    public BackStar(float fx, float fy, float fw, float fh, float maxR) {
        isBackStar = false;
        this.fx = fx;
        this.fy = fy;
        this.fw = fw;
        this.fh = fh;
        this.maxR = maxR;
        PI_restoreTime = (float) (Math.PI / restoreTime);
    }

    public void start() {
        isRandomY0 = true;
        restart();
        offsetTicks = Utils.getRandom(restoreTime);
    }

    public void restartWithRandomOffsetTicks() {
        restart();
        offsetTicks = Utils.getRandom(restoreTime);
    }

    public void stop() { isStop = true; }

    private void restart() {
        if (isBackStar) {
            this.x = Utils.getRandom(w);
            this.y0 = Utils.getRandom(h);
        } else {
            this.w = (int)(fw * MetrixUtils.screen_metrix_width);
            this.h = (int)(fh * MetrixUtils.screen_metrix_height);
            this.x = (int)(fx * MetrixUtils.screen_metrix_width) + Utils.getRandom(w);
            this.y0 = (int)(fy * MetrixUtils.screen_metrix_height);
            if (isRandomY0)
                this.y0 += Utils.getRandom(h);
        }
        this.r = Utils.getRandom((int)(this.maxR * 1000)) / 1000;
        this.moveK = r / maxR; //random.nextFloat();
        this.maxAlpha = Utils.getRandom(128);
        this.offsetTicks = 0;
        startTicks = 0;
        isVisible = true;
    }

    private long startTicks = 0;
    public int nextFrame(long ticks, boolean isMoving) {
        if (isVisible) {
            if (a <= this.maxAlpha) {
                if (startTicks == 0)
                    startTicks = ticks;
                long delta = ticks - startTicks - offsetTicks;
                if (delta >= 0) {
                    if (delta > restoreTime) {
                        if (isStop)
                            isVisible = false;
                        else
                            restartWithRandomOffsetTicks();
                    } else {
                        k = (float) Math.abs(Math.sin(PI_restoreTime * delta));
                        a = (int) (this.maxAlpha * k);
                    }

                    if (isMoving)
                        this.y = this.y0 + (int) (this.h * moveK * (ticks - startTicks) / moveDuration);
                    else
                        this.y = this.y0;
                }
            } else
                a = 0;
            return a;
        }
        return 0;
    }

    public float getR() { return r * k; }

    @Override
    public void recycle() { }
}
