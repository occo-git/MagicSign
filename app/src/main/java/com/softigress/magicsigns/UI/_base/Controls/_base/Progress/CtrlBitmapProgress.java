package com.softigress.magicsigns.UI._base.Controls._base.Progress;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;

public class CtrlBitmapProgress extends DrawingBase {

    private float percent;
    private Matrix m0, m1;

    public CtrlBitmapProgress(float fx, float fy, float fd, Bitmap bitmap) {
        super(fx, fy, fd);
        setDefaultBitmap(bitmap);
    }

    public void setPercent(float percent) {
        if (percent < 0)
            percent = 0;
        if (percent > 1)
            percent = 1;
        this.percent = percent;
    }

    @Override
    public void drawFrame(Canvas c) {
        calc();
        if (currentBitmap != null) {
            float h0 = (1 - percent) * h;
            Bitmap b = Bitmap.createBitmap((int) w, (int) h, Bitmap.Config.ARGB_8888);
            m0 = new Matrix();
            m0.setScale(this.w / currentBitmap.getWidth(), this.h / currentBitmap.getHeight());
            m0.postTranslate(0, -h0);
            Canvas c1 = new Canvas(b);
            c1.drawBitmap(currentBitmap, m0, paint);

            m1 = new Matrix();
            m1.setTranslate(x - .5f * w, y - .5f * h + h0);
            c.drawBitmap(b, m1, paint);
            //b.recycle();
        }
    }

    @Override
    public void recycle() {
        super.recycle();

        m0 = null;
        m1 = null;
    }
}