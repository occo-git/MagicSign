package com.softigress.magicsigns.UI._base.Effects.Flash;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Keep;

import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.Utils.PaintUtils;

public class Flash implements IDrawing {

    public final static long showDuration = 250;
    public final static long hideDuration = 250;
    private Paint paint;
    private int alpha = 0;

    public Flash() {
        paint = PaintUtils.getPaintWhite(0);
        off();
    }

    @Keep
    private void setAlpha(int a) { this.alpha = a; }

    private void on() { setAlpha(255); }

    private void off() {
        setAlpha(0);
    }

    public void show() {
        off();
        ObjectAnimator.ofInt(this, "Alpha", 0, 255).setDuration(showDuration).start();
    }

    public void hide() {
        on();
        ObjectAnimator.ofInt(this, "Alpha", 255, 0).setDuration(hideDuration).start();
    }

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    public void calc() { }

    public void drawFrame(Canvas c) {
        paint.setAlpha(this.alpha);
        if (this.alpha > 0)
            c.drawPaint(paint);
    }
    //endregion

    public void recycle() {
        paint = null;
    }
}
