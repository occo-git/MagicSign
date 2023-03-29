package com.softigress.magicsigns.UI._base.Effects.Glares;

import android.graphics.Canvas;
import android.os.SystemClock;
import com.softigress.magicsigns._Base.ArrayRecyclableSimple;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;

public class Glares implements IDrawing {

    private static final float glareFd = .02666f;
    private final ArrayRecyclableSimple<Glare> glares = new ArrayRecyclableSimple<>(Glare.class);
    private final Glare[] glareItems;
    private boolean isVisible = false;

    public Glares(float fx, float fy, float fw, float fh, float glareFd, int count) {
        //if (MetrixUtils.screen_metrix_width > 0) {
            for (int i = 0; i < count; i++) {
                Glare g = new Glare(glareFd);
                g.setBounds(fx, fy, fw, fh);
                this.glares.add(g);
            }
            glareItems = glares.getItems();
        //}
    }

    public Glares(float fx, float fy, float fw, float fh, int count) {
        this(fx, fy, fw, fh, glareFd, count);
    }

    public void setBounds(float fx, float fy, float fw, float fh) {
        for (Glare g : glareItems)
            g.setBounds(fx, fy, fw, fh);
    }
    public void setNotInBounds(float fw, float fh) {
        for (Glare g : glareItems)
            g.setNotInBounds(fw, fh);
    }

    //region show / hide
    public boolean isVisible() { return isVisible; }

    public void show() {
        if (!isVisible) {
            isVisible = true;
            for (Glare g : glareItems)
                g.start();
        }
    }

    public void hide() {
        if (isVisible) {
            isVisible = false;
            for (Glare g : glareItems)
                g.stop();
        }
    }
    //endregion

    //region IDrawing
    @Override
    public int getLayer() { return 1; } // для отображений в drawing group над остальными элементами

    @Override
    public void calc() { }

    @Override
    public void drawFrame(Canvas canvas) {
        long ticks = SystemClock.elapsedRealtime();
        for (Glare g : glareItems) {
            g.nextFrame(ticks);
            g.drawFrame(canvas);
        }
    }
    //endregion

    @Override
    public void recycle() {
        if (glares != null)
            glares.recycle();
    }
}
