package com.softigress.magicsigns._Base._Drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;

public class DrawingFrameRate implements IDrawing{

    private String name;
    private final float fx, fy;
    private Paint p;

    public DrawingFrameRate(String name, float fx, float fy) {
        this.name = name;
        this.fx = fx;
        this.fy = fy;
        this.p = PaintUtils.getPaintWhite(255);
        this.p.setTextSize(30);
        this.p.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public int getLayer() { return 0; }

    private float x, y;
    @Override
    public void calc() {
        // point
        this.x = MetrixUtils.screen_metrix_width * fx;
        this.y = MetrixUtils.screen_metrix_height * fy;
    }

    private long startTicks;
    public void start() { startTicks = SystemClock.elapsedRealtime(); }

    public void drawFrame(Canvas c) {
        if (CurrentSettings.isDrawFPS) {
            calc();
            if (startTicks > 0)
                c.drawText(String.format("FR (%1$s): %2$d", name, SystemClock.elapsedRealtime() - startTicks), x, y, p);
        }
    }

    @Override
    public void recycle() {
        if (name != null)
            name = null;
        if (p != null)
            p = null;
    }
}
