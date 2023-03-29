package com.softigress.magicsigns.UI._base.Effects.Glares;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._system.Utils.Utils;

class Glare extends DrawingBase {

    private float fx0, fy0, fw0, fh0; // область в которой может появиться glare
    private float fw1, fh1; // область в которой не может появиться glare (fw1 < fw0, fh1 < fh0)

    private boolean isOnStop = false;
    private int offsetTicks;
    private static final int restoreTime = 3000;
    private int realRestoreTime = restoreTime;
    private float PI_restoreTime = (float)(Math.PI / realRestoreTime);

    public Glare(float glareFd) {
        super(glareFd, R.string.bmp_glare_01);
        setAlpha(0);
    }

    public void setBounds(float fx0, float fy0, float fw0, float fh0) {
        this.fx0 = fx0;
        this.fy0 = fy0;
        this.fw0 = fw0;
        this.fh0 = fh0;
    }

    public void setNotInBounds(float fw1, float fh1) {
        this.fw1 = fw1;
        this.fh1 = fh1;
    }

    private boolean isStarted = false;
    public void start() {
        if (!isStarted) {
            restart();
            realRestoreTime = restoreTime + Utils.getRandom(restoreTime / 10);
            PI_restoreTime = (float)(Math.PI / realRestoreTime);
            offsetTicks = Utils.getRandom(realRestoreTime);
        }
    }

    private void restart() {
        setAlpha(0);
        setScale(0f);
        setPoint(fx0 + Utils.getRandomMathSign() * Utils.getRandomBetween(fw1, fw0) * .5f,
                 fy0 + Utils.getRandomMathSign() * Utils.getRandomBetween(fh1, fh0) * .5f);
        isOnStop = false;
        offsetTicks = 0;
        startTicks = 0;
        isStarted = true;
    }

    public void stop() {
        isOnStop = true;
    }

    private long startTicks = 0;
    public void nextFrame(long ticks) {
        if (isStarted) {
            if (startTicks == 0)
                startTicks = ticks;
            long delta = ticks - startTicks + offsetTicks;
            if (delta > realRestoreTime) {
                if (isOnStop) {
                    isStarted = false;
                    setAlpha(0);
                } else
                    restart();
            } else if (delta >= 0) {
                setScale((float) Math.abs(Math.sin(PI_restoreTime * delta)));
                setAlpha((int) (255 * Math.abs(Math.sin(PI_restoreTime * delta))));
            }
        }
    }

    /*@Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);
        float x0 = fx0 * MetrixUtils.screen_metrix_width;
        float y0 = fy0 * MetrixUtils.screen_metrix_height;
        float w2 = fw0 * MetrixUtils.screen_metrix_width / 2f;
        float h2 = fh0 * MetrixUtils.screen_metrix_height / 2f;
        c.drawRect(x0 - w2, y0 - h2, x0 + w2, y0 + h2, PaintUtils.getPaintStrokeWhite(255, PaintUtils.getStrokeWidth()));
    }*/
}
