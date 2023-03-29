package com.softigress.magicsigns.UI._base.Effects.Stars.OutStars;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.softigress.magicsigns._Base._Drawing._base.DrawingSimple;

public class OutStar extends DrawingSimple {

    private Matrix m = new Matrix();

    public OutStar(float starFr) {
        super(starFr);
        setAlpha(0);
    }

    public void drawFrame(Canvas c, Bitmap b) {
        super.drawFrame(c);
        float rr = scale * r;
        m.reset();
        m.setScale(rr / b.getWidth(), rr / b.getHeight());
        m.postTranslate(x - rr, y - rr);
        c.drawBitmap(b, m, paint);
    }
}
