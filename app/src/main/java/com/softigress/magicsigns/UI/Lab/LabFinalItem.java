package com.softigress.magicsigns.UI.Lab;

import android.graphics.Canvas;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Effects.Glares.Glares;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBaseTouchable;

public class LabFinalItem extends DrawingBaseTouchable {

    public static final float fdItem = .32f;
    private Glares glares;

    public LabFinalItem(float fx, float fy) {
        super(fdItem);

        setDefaultBitmap(R.string.bmp_potion_10_final);
        setPoint(fx, fy);

        float fr = fdItem / 2f;
        glares = new Glares(fx, fy, fr, fr, 5);
    }

    //region show / hide
    @Override
    public void show() {
        super.show();
        if (glares != null)
            glares.show();
    }

    @Override
    public long hide() {
        long duration = super.hide();
        if (glares != null)
            glares.hide();
        return duration;
    }
    //endregion

    //region draw
    @Override
    public void calc() {
        super.calc();
        float fr = fdItem / 2f;
        if (glares != null)
            glares.setBounds(fx, fy, fr, fr);
    }

    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);
        if (glares != null)
            glares.drawFrame(c);
    }
    //endregion

    @Override
    public void recycle() {
        super.recycle();
        if (glares != null)
            glares.recycle();
        glares = null;
    }
}
