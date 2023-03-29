package com.softigress.magicsigns.UI._base.Effects.Stars.FallingStars;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._system.Utils.Utils;

class FallingStar extends DrawingBase {

    private final float boundFx, boundFy, boundFw, boundFh; // область появления звезды
    private final float maxFd;
    private int maxAlpha;

    private float fy0;
    private final float PI_restoreTime;
    private int offsetTicks;
    private static final int restoreTime = 10000;
    private static final int moveDuration = 20000; // время перемещения по высоте this.h
    private float moveK = 1f; // коэффициент для корректировки скорости перемещения

    public FallingStar(float fx, float fy, float fw, float fh, float maxFd) {
        super(maxFd, R.string.bmp_star);
        boundFx = fx;
        boundFy = fy;
        boundFw = fw;
        boundFh = fh;
        this.maxFd = maxFd;
        PI_restoreTime = (float)(Math.PI / restoreTime);

        restartWhithRandomOffsetTicks();
    }

    public void restartWhithRandomOffsetTicks() {
        restart();
        show();
    }

    private void restart() {
        float ffx = boundFx + boundFw * Utils.getRandom();
        float ffy = boundFy + boundFh * Utils.getRandom();
        this.fy0 = ffy;
        setPoint(ffx, ffy);
        setFd(maxFd * Utils.getRandom());
        this.moveK = fd / maxFd;
        this.maxAlpha = Utils.getRandom(200);
        this.offsetTicks = Utils.getRandom(restoreTime);
        startTicks = 0;
    }

    private long startTicks = 0;
    public void nextFrame(long ticks, boolean isMoving) {
        int a = alpha;
        if (a <= this.maxAlpha) {
            if (startTicks == 0)
                startTicks = ticks;
            long delta = ticks - startTicks + offsetTicks;
            if (delta > restoreTime)
                restart();
            else if (delta >= 0)
                a = (int)(this.maxAlpha * Math.abs(Math.sin(PI_restoreTime * delta)));
            float ffy =  isMoving ? this.fy0 + this.boundFh * moveK * (ticks - startTicks) / moveDuration : this.fy0;
            setPoint(fx, ffy);
        }
        else
            a = 0;
        setAlpha(a);
    }
}
