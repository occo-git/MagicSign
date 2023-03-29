package com.softigress.magicsigns.UI._base.Controls._base.Progress;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.SystemClock;
import androidx.annotation.Keep;

import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns.UI._base.Groups.Dialogs.IDialogItemControl;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.TextUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class CtrlProgressBar implements IDialogItemControl {

    private float x, y, w, h;
    private float fx, fy, fw, fh;
    private int alpha;
    private int red, green, blue;
    private DrawingHAlign halign = DrawingHAlign.CENTER;

    private boolean isVisible;
    public boolean isHideOnZero = true;
    private boolean isFinished;
    public boolean isHideOnFinished = false;

    private float percent;
    private float animPercent;
    public Integer getCurrentAnimPercent() { return (int)(animPercent * 100); }

    public int fileId; // используется при скачивании файлов
    private String index;
    private DrawingText txtIndex;
    private Paint paint0;
    private Paint p1, p2;

    @Keep
    public void setFx(float fx) {
        if (this.fx != fx) {
            this.fx = fx;
            onChange();
        }
    }
    @Keep
    public void setFy(float fy) {
        if (this.fy != fy) {
            this.fy = fy;
            onChange();
        }
    }
    @Keep
    public void setFw(float fw) {
        if (this.fw != fw) {
            this.fw = fw;
            onChange();
        }
    }
    @Keep
    public void setFh(float fh) {
        if (this.fh != fh) {
            this.fh = fh;
            onChange();
        }
    }
    @Keep
    public void setAlpha(int a) {
        if (txtIndex != null)
            txtIndex.setAlpha(a);
        paint0.setAlpha(a / 6);

        if (this.alpha != a) {
            this.alpha = a;
            onChange();
        }
    }
    public void setAlign(DrawingHAlign halign) {
        this.halign = halign;
        if (txtIndex != null)
            txtIndex.setTextAlign(halign);
    }

    private void onChange() {
        calc();
        onGradientChange();
    }

    public CtrlProgressBar(float fw, float fh) {
        this.fw = fw;
        this.fh = fh;

        int maxAlpha = 255;
        p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint0 = PaintUtils.getPaintWhite(maxAlpha / 6);
        //paint0.setStrokeWidth(PaintUtils.strokeWidth);
        this.setAlpha(maxAlpha);
        onChange();
    }

    public CtrlProgressBar(float fx, float fy, float fw, float fh) {
        this(fw, fh);
        this.fx = fx;
        this.fy = fy;
        onChange();
    }

    public void setPoint(float fx, float fy) {
        if (this.fx != fx || this.fy != fy) {
            this.fx = fx;
            this.fy = fy;
            if (txtIndex != null)
                txtIndex.setPoint(fx, fy);
            onChange();
        }
    }

    public void setRGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        onChange();
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public void setIndexText(String index) {
        this.index = index;
        if (txtIndex == null) {
            txtIndex = new DrawingText(halign, Typeface.DEFAULT, TextUtils.controls_progress_index);
            txtIndex.setTextBack(4f, 32, 255, 255, 255);
            txtIndex.setPoint(fx, fy);
            txtIndex.setAlpha(alpha);
        }
        txtIndex.setText("  " + index + "  ");
    }

    public void reset() {
        this.oldPercent = 0;
        this.newPercent = 0;
        this.animPercent = 0;
        this.percent = 0;
        this.isFinished = false;
    }

    public void fill() {
        this.oldPercent = 1f;
        this.newPercent = 1f;
        this.animPercent = 1f;
        this.percent = 1f;
        this.step = 0;
        this.isFinished = true;
    }

    public float getPercent() { return percent; }
    public void setPercent(float newPercent) {
        try {
            if (newPercent < 0) newPercent = 0;
            if (newPercent > 1) newPercent = 1;
            this.oldPercent = percent;
            this.newPercent = newPercent;
            this.percent = newPercent;
            this.startAnimTicks = 0;
            this.step = (this.newPercent - this.oldPercent) / animDuration;

        } catch (Throwable t) {
            Utils.CrashReport("CtrlBarProgress.setPercent [" + newPercent + "]", t);
        }
    }

    //region show hide
    public void show() {
        if (isHideOnFinished && isFinished)
            return;
        isVisible = true;
    }
    public long hide() {
        isVisible = false;
        return 0;
    }
    //endregion

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    private RectF rect0 = new RectF();
    float x0, y0, y1;
    public void calc() {
        this.x = MetrixUtils.screen_metrix_width * fx;
        this.y = MetrixUtils.screen_metrix_height * fy;
        this.w = MetrixUtils.screen_metrix_width * fw;
        this.h = MetrixUtils.screen_metrix_height * fh;
        if (halign == DrawingHAlign.LEFT)
            x0 = x;
        else if (halign == DrawingHAlign.CENTER)
            x0 = x - .5f * w;
        else if (halign == DrawingHAlign.RIGHT)
            x0 = x - w;
        float h2 = h / 2f;
        y0 = y - h2;
        y1 = y + h2;

        rect0.left = x0;
        rect0.top = y0;
        rect0.right = x0 + w;
        rect0.bottom = y1;

        if (paint0 != null)
            paint0.setStrokeWidth(PaintUtils.strokeWidth);
    }

    public boolean nextStep() {
        if (this.step > 0) {
            long ticks = SystemClock.elapsedRealtime();
            if (startAnimTicks == 0)
                startAnimTicks = ticks;
            long delta = ticks - startAnimTicks;
            if (delta <= animDuration)
                animPercent = oldPercent + this.step * delta;
            else
                animPercent = newPercent;
        } else
            animPercent = newPercent;

        if (animPercent >= 1f) {
            fill();//isFinished = true;
            if (isHideOnFinished) {
                hide();
                return false;
            }
        }

        return true;
    }

    private LinearGradient gradient0, gradient1;
    private void onGradientChange() {
        float aa = alpha / 255f;
        float k0 = 160 / 255f;

        gradient0 = new LinearGradient(x0, y0, x0, y1,
                Color.argb(alpha, (int)(k0 * red), (int)(k0 * green), (int)(k0 * blue)),
                Color.argb(alpha, red, green, blue),
                Shader.TileMode.MIRROR);

        float hh = h;
        float ww = w * animPercent;
        if (hh > ww)
            hh = ww;
        float y2 = y - hh / 2f;
        gradient1 = new LinearGradient(x0, y2, x0, y2 + hh / 3,
                Color.argb((int) (120 * aa), 255, 255, 255),
                Color.argb((int) (64 * aa), 255, 255, 255),
                Shader.TileMode.MIRROR);
    }

    private static final long animDuration = 1000;
    private float oldPercent;
    private float newPercent;
    private long startAnimTicks = 0;
    private float step;
    private RectF rect = new RectF();
    public void drawFrame(Canvas c) {
        try {
            if (isVisible && !(isHideOnZero && newPercent == 0)) {
                calc();

                //region progress
                if (nextStep()) {

                    if (w > 0 && h > 0) {
                        float ww = w * animPercent;
                        float x1 = x0 + ww;
                        float hh = h;
                        float h2 = h / 2f;
                        float y0 = y - h2;
                        float y1 = y + h2;
                        if (rect0 != null)
                            c.drawRoundRect(rect0, h2, h2, paint0);

                        if (hh > ww)
                            hh = ww;
                        y0 = y - hh / 2f;
                        y1 = y0 + hh;
                        float r2 = hh / 2f;
                        float r4 = hh / 4f;

                        if (animPercent > 0) {
                            if (gradient0 != null) {
                                p1.setShader(gradient0);
                                rect.left = x0;
                                rect.top = y0;
                                rect.right = x1;
                                rect.bottom = y1;
                                c.drawRoundRect(rect, r2, r2, p1);
                            }
                            if (gradient1 != null) {
                                p2.setShader(gradient1);
                                rect.left = x0 + r4;
                                rect.top = y0;
                                rect.right = x1 - r4;
                                rect.bottom = y0 + hh / 3;
                                c.drawRoundRect(rect, r4, r4, p2);
                            }

                            //if (x1 > x0 + r)
                            //    c.drawCircle(x1 - r, y0 + r, rr, paintWhite1);
                        }
                    }
                    //endregion

                    if (txtIndex != null)
                        txtIndex.drawFrame(c);
                }
            }
        } catch (Throwable t) {
            Utils.CrashReport("CtrlBarProgress.drawFrame", t);
        }
    }
    //endregion

    public void recycle() {
        if (txtIndex != null)
            txtIndex.recycle();

        txtIndex = null;
        paint0 = null;
        p1 = null;
        p2 = null;
    }
}
