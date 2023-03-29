package com.softigress.magicsigns.UI._base.Controls._base.Buttons;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.TextUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class BtnImageLabel extends DrawingButton {

    private int id;
    private DrawingBase drawing;
    private DrawingText txtLabel;
    private Paint paint0, paint1;

    public BtnImageLabel(float fx, float fy, float fw, float fh) {
        super(fx, fy, fw, fh);

        ObjectAnimator anim = ObjectAnimator.ofFloat(this, "scale", 1f, 0.975f, 1f).setDuration(clickDuration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        applyClickAnimator(anim);

        paint0 = PaintUtils.getPaintStrokeWhite(255 / 2, PaintUtils.strokeWidth);
        paint1 = PaintUtils.getPaint(255, 64, 64, 64);

        show();
    }

    public BtnImageLabel(int id, float fx, float fy, float fw, float fh) {
        this(fx, fy, fw, fh);

        this.id = id;
    }

    public void setBitmap(int bitmapId) {
        Bitmap bitmap = Utils.getBitmap(bitmapId);
        if (bitmap != null)
            drawing = new DrawingBase(.62f * fh, bitmap);
    }

    public void setLabelText(String text, Typeface typeface) {
        this.setLabelText(text, typeface, TextUtils.dialog_button_text);
    }
    public void setLabelText(String text, Typeface typeface, int size) {
        txtLabel = new DrawingText(DrawingHAlign.CENTER, typeface, size);
        txtLabel.setTextBack(2f, 128, 0, 0, 0);
        txtLabel.setText(text);
    }

    public void setStrokeWidth(float strokeWidth) {
        if (paint0 != null)
            paint0.setStrokeWidth(strokeWidth);
    }

    @Override
    public void calc() {
        super.calc();
        float ffd = fh * MetrixUtils.screen_K_default / MetrixUtils.screen_K;
        if (paint0 != null)
            paint0.setAlpha(this.alpha);
        if (paint1 != null)
            paint1.setAlpha(this.alpha);
        if (drawing != null) {
            drawing.setAlpha(this.alpha);
            drawing.setPoint(this.fx - fw / 2 + ffd, this.fy);
            drawing.calc();
        }
        if (txtLabel != null) {
            txtLabel.setAlpha(this.alpha);
            if (drawing != null) {
                txtLabel.setTextAlign(DrawingHAlign.LEFT);
                txtLabel.setPoint(fx - fw / 2 + 2 * ffd, fy);
            } else {
                txtLabel.setTextAlign(DrawingHAlign.CENTER);
                txtLabel.setPoint(fx + fw / 2, fy + fh / 2);
            }
            txtLabel.calc();
        }
    }

    @Override
    public void drawBackground(Canvas c) {
        super.drawBackground(c);

        float x0 = x - w / 2f;
        float y0 = y - h / 2f;
        float r = h / 4f;

        c.drawRoundRect(new RectF(x0, y0, x0 + w, y0 + h), r, r, paint1);
        c.drawRoundRect(new RectF(x0, y0, x0 + w, y0 + h), r, r, paint0);

        /*Paint p1 = new Paint();
        p1.setShader(new LinearGradient(x0, y0, x0, y0 + h, Color.argb(255, 32, 32, 32), Color.argb(255, 128, 128, 128), Shader.TileMode.MIRROR));
        c.drawRoundRect(new RectF(x0, y0, x0 + w, y0 + h), r, r, p1);
        Paint p2 = new Paint();
        p2.setShader(new LinearGradient(x0, y0, x0, y0 + h / 3, Color.argb(120, 255, 255, 255), Color.argb(0, 255, 255, 255), Shader.TileMode.MIRROR));
        c.drawRoundRect(new RectF(x0, y0, x0 + w, y0 + h / 3), r, r, p2);*/
    }

    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);
        if (isVisible()) {
            if (drawing != null)
                drawing.drawFrame(c);
            if (txtLabel != null)
                txtLabel.drawFrame(c);
        }
    }

    @Override
    public void recycle() {
        super.recycle();
        if (drawing != null)
            drawing.recycle();
        if (txtLabel != null)
            txtLabel.recycle();

        drawing = null;
        txtLabel = null;
        paint1 = null;
        paint0 = null;
    }
}