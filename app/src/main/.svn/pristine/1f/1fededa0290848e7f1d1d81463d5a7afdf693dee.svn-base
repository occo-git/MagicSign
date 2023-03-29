package com.softigress.magicsigns._system.Settings.Infos;

import android.graphics.PointF;

import com.softigress.magicsigns._Base.IRecycle;

public class SignArea implements IRecycle {

    public final float x, y, r;

    public SignArea(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public boolean inBounds(PointF p) {
        return p.x >= x - r && p.x <= x + r && p.y >= y - r && p.y <= y + r;
    }

    public boolean inBoundsAny(PointF[] points) {
        for (PointF p : points) {
            if (inBounds(p))
                return true;
        }
        return false;
    }

    @Override
    public void recycle() { }
}