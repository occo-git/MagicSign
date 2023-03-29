package com.softigress.magicsigns.UI._base.Effects.BackStars;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;

import com.softigress.magicsigns._Base.ArrayRecyclableSimple;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;

public class BackStars implements IDrawing {

    private static final float maxFr = .01f;
    private final ArrayRecyclableSimple<BackStar> backStars = new ArrayRecyclableSimple<>(BackStar.class);
    private final BackStar[] backStarItems;
    private final int count;
    public boolean isMoving = false;
    private Paint paint;

    public BackStars(int w, int h, int count) {
        this.count = count;
        synchronized (backStars) {
            if (MetrixUtils.screen_metrix_width > 0)
                for (int s = 0; s <= this.count; s++)
                    backStars.add(new BackStar(w, h, maxFr * MetrixUtils.screen_metrix_width));
        }
        backStarItems = backStars.getItems();
        paint = PaintUtils.getPaintWhite(255);
    }

    public BackStars(float fx, float fy, float fw, float fh, int count, int moveDuration) {
        this.count = count;
        synchronized (backStars) {
            if (MetrixUtils.screen_metrix_width > 0)
                for (int s = 0; s <= this.count; s++) {
                    BackStar backStar = new BackStar(fx, fy, fw, fh, maxFr * MetrixUtils.screen_metrix_width);
                    backStar.moveDuration = moveDuration;
                    backStars.add(backStar);
                }
        }
        backStarItems = backStars.getItems();
        paint = PaintUtils.getPaintWhite(255);
    }

    public void start() {
        for (BackStar s : backStarItems)
            s.start();
    }

    public void refresh() {
        for (BackStar s : backStarItems)
            s.restartWithRandomOffsetTicks();
    }

    public void stop() {
        for (BackStar s : backStarItems)
            s.stop();
    }

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    @Override
    public void calc() { }

    @Override
    public void drawFrame(Canvas c) {
        long ticks = SystemClock.elapsedRealtime();
        //if (backStars != null)
            //synchronized (backStars) {
                for (BackStar s : backStarItems) {
                    if (s != null && paint != null) {
                        paint.setAlpha(s.nextFrame(ticks, isMoving));
                        c.drawCircle(s.x, s.y, s.getR(), paint);
                    }
                }
            //}
    }
    //endregion

    @Override
    public void recycle() {
        synchronized (backStars) {
            //if (backStars != null)
                backStars.recycle();
            //backStars = null;
        }
        //paint = null;
    }
}
