package com.softigress.magicsigns._Base._Drawing._base;

import android.graphics.Canvas;

import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class DrawingSimpleNoPaint implements IDrawing {

    private float fr;
    private float fx;
    private float fy;
    protected float r, x, y;
    private float angel = 0;
    protected float angelSin = 0f;
    protected float angelCos = 1f;

    public float getFr() { return this.fr; }
    public float getFx() { return this.fx; }
    public float getFy() { return this.fy; }

    public void setFr(float fr) { this.fr = fr; }
    public void setFx(float fx) { this.fx = fx; }
    public void setFy(float fy) { this.fy = fy; }
    public void setAngel(float angel) {
        if (angel > 360 || angel < -360)
            angel = angel % 360;
        this.angel = angel;
    }
    public void setAngelRadians(float angelRadians) {
        setAngel(Utils.PI_180 * angelRadians);
        angelCos = (float)Math.cos(angelRadians);
        angelSin = (float)Math.sin(angelRadians);
    }

    public DrawingSimpleNoPaint(float fr) {
        this.fr = fr;
    }

    public void setPoint(float fx, float fy) {
        this.fx = fx;
        this.fy = fy;
    }

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    @Override
    public void calc() {
        r = MetrixUtils.screen_metrix_height * fr;
        x = MetrixUtils.screen_metrix_width * fx;
        y = MetrixUtils.screen_metrix_height * fy;
    }

    @Override
    public void drawFrame(Canvas c) {
        calc();
    }
    //endregion

    @Override
    public void recycle() { }
}
