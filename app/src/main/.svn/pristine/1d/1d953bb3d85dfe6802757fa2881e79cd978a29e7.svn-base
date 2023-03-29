package com.softigress.magicsigns.UI._base.Controls.UserPhoto;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBaseTouchable;
import com.softigress.magicsigns._system.Utils.PaintUtils;

public class UserPhoto extends DrawingBaseTouchable {

    private static final int maxPaint0a = 32;
    private static final int maxPaint1a = 128;
    private final Paint paint0, paint1;

    public UserPhoto(float fx, float fy, float fd) {
        super(fx, fy, fd);

        paint0 = PaintUtils.getPaintWhite(maxPaint0a);
        paint1 = PaintUtils.getPaintStrokeWhite(maxPaint1a, PaintUtils.strokeWidth);
    }

    public void setPhoto(Bitmap b) {
        if (b != null) {
            setDefaultMaskedBitmap(b, R.string.bmp_photo_mask);
            refreshCurrentStatus();
        } else
            hide();
    }

    @Override
    public void drawBackground(Canvas c) {
        super.drawBackground(c);
        float da = alpha / 255f;
        paint0.setAlpha((int)(maxPaint0a * da));
        paint1.setAlpha((int)(maxPaint1a * da));

        c.drawCircle(x, y, .6f * w, paint0);
        c.drawCircle(x, y, .5f * w, paint1);
    }

    //@Override
    //public void recycle() {
    //    super.recycle();
    //}
}
