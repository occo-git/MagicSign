package com.softigress.magicsigns.Game.Dna._base;

import android.graphics.Canvas;

import com.softigress.magicsigns._Base._Drawing._base.DrawingSimple;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class DrawingDnaPod extends DrawingSimple {

    private DnaPodStatus status = DnaPodStatus.OFF;
    public long duration = 10500;
    private long offset = 0;

    private float fl;
    private float l;
    //public boolean isHorisontal = true;

    public float getFl() { return this.fl; }
    public void setFl(float fl) { this.fl = fl; }

    public DrawingDnaPod(float fr, float fl) {
        super(fr);
        this.fl = fl;
    }
    public DrawingDnaPod(float fr, long duration) {
        super(fr);
        this.duration = duration;
    }

    public void setOffset(long offset) { this.offset = offset; }

    //region status
    public void setStatusOff() {
        status = DnaPodStatus.OFF;
        setColorOff();
    }
    public void setStatusOn() {
        if (status == DnaPodStatus.OFF) {
            status = DnaPodStatus.ON;
            setColorOn();
        }
    }
    public void setStatusReady() {
        status = DnaPodStatus.READY;
        setColorReady();
    }
    private void setColorOn() { setColor(255, 255, 255, 255); }
    private void setColorOff() { setColor(255, 96, 96, 96); }
    private void setColorReady() { setColor(255, 128, 255, 96); }
    //endregion

    //region Draw
    private float p0, p1, rk;
    public void nextFrame(long delta) {
        calc();
        float lK = (float)((delta + offset) % duration) / duration;
        float k = (float)Math.cos(Utils.PI2 * lK);
        rk = (float)Math.cos(Utils.PI2 * lK + Utils.PI_2);
        //if (isHorisontal) {
            l = MetrixUtils.screen_metrix_height * fl * k;
            p0 = y + l;
            p1 = y - l;
        /*} else {
            l = MetrixUtils.screen_metrix_width * fl * k;
            p0 = x + l;
            p1 = x - l;
        }*/
    }

    @Override
    public void drawFrame(Canvas c) {
        //super.drawFrame(c);
        //if (isHorisontal) {
            c.drawCircle(x, p0, r * (1f - .25f * rk), paint);
            c.drawCircle(x, p1, r * (1f + .25f * rk), paint);
            c.drawLine(x, p0, x, p1, paint1);
        /*} else {
            c.drawCircle(p0, y, r * (1f + .25f * rk), paint);
            c.drawCircle(p1, y, r * (1f - .25f * rk), paint);
            c.drawLine(p0, y, p1, y, paint1);
        }*/
    }
    //endregion

}
