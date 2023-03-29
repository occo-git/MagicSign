package com.softigress.magicsigns.UI._base.Controls._base.Sliders;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBaseTouchable;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;

public class SliderBase extends DrawingBaseTouchable {

    private boolean isEnabled = true;
    private float volume = .5f;
    private float ffw, ww; // постоянная ширина (не зависящая от ширины окна)

    private Paint paint0, paint1;

    public SliderBase(float fx, float fy, float fw, float fh) {
        super(fx, fy, fw, fh);
        ffw = fw * MetrixUtils.screen_K_default / MetrixUtils.screen_K;
        setAlign(DrawingHAlign.LEFT, DrawingVAlign.CENTER);

        paint.setColor(Color.WHITE);
        paint0 = PaintUtils.getPaintStrokeWhite(32, PaintUtils.strokeWidth);
        paint1 = PaintUtils.getPaintStrokeWhite(255, PaintUtils.strokeWidth);
    }

    private ISliderListener listener;
    public void setListener(ISliderListener l) { listener = l; }

    public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }

    public void setVolume(float vol) {
        if (vol < 0f)
            vol = 0f;
        if (vol > 1f)
            vol = 1f;
        volume = vol;
        if (listener != null)
            listener.onVolumeChanged(volume);
    }
    public float getVolume() { return volume; }

    //region Touch
    private boolean isTouched = false;
    @Override
    public boolean onTouch(int px, int py) {
        if (isEnabled) {
            if (super.onTouch(px, py)) {
                isTouched = true;
                setVolume((px - x - h / 2) / (ww - h));
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean onTouchUp(int px, int py) {
        if (isEnabled) {
            super.onTouchUp(px, py);
            if (isTouched) {
                isTouched = false;
                if (listener != null)
                    listener.onRelease();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onMoveTo(int px, int py) {
        if (isEnabled) {
            super.onMoveTo(px, py);
            if (isTouched) {
                setVolume((px - x - h / 2) / (ww - h));
            }
        }
    }
    //endregion

    @Override
    public void setAlpha(int a) {
        super.setAlpha(a);
        paint0.setAlpha(a / 8);
        paint1.setAlpha(a);
    }

    @Override
    public void calc() {
        super.calc();
        ww = ffw * MetrixUtils.screen_metrix_width;
    }

    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);

        float h2 = h / 2;
        float x2 = x + (ww - h) * volume + h2;
        c.drawLine(x + h2, y, x + ww - h2, y, paint0);
        c.drawLine(x + h2, y, x2, y, paint1);
        if (isEnabled) {
            c.drawCircle(x2, y, h2 / (isTouched ? 2 : 1), paint);
            c.drawCircle(x2, y, h2, isTouched ? paint0 : paint1);
        }
    }

    @Override
    public void recycle() {
        super.recycle();

        paint0 = null;
        paint1 = null;
    }
}
