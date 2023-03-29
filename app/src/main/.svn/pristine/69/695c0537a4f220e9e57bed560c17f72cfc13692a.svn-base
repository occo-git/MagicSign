package com.softigress.magicsigns.UI._base.Controls._base.Texts;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;

public class DrawingRect extends DrawingBase {

    private static final float rectFr = .05f; // радиус скругления обводки
    private int maxRectAlpha = 48;
    private RectF rect;
    private Paint rectPaint0;    // контур
    private Paint rectPaint1;    // заполнение

    public DrawingRect() {
        super();

        rectPaint0 = PaintUtils.getPaintWhite(maxRectAlpha / 2);
        rectPaint0.setStrokeWidth(PaintUtils.strokeWidth * 4);
        rectPaint1 = PaintUtils.getPaintWhite(maxRectAlpha / 2);
    }

    @Override
    public void setAlpha(int a) {
        super.setAlpha(a);

        float a255 = alpha / 255f;
        int aa = (int) (maxRectAlpha * a255 / 2f);
        if (rectPaint0 != null)
            rectPaint0.setAlpha(aa);
        if (rectPaint1 != null)
            rectPaint1.setAlpha(aa);
    }

    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);

        rect = new RectF(x, y, w, h);
        float rfr = rectFr * MetrixUtils.screen_metrix_width;
        if (rectPaint0 != null)
            c.drawRoundRect(rect, rfr, rfr, rectPaint0); // заполнение0
        if (rectPaint1 != null)
            c.drawRoundRect(rect, rfr, rfr, rectPaint1); // заполнение1
    }
}
