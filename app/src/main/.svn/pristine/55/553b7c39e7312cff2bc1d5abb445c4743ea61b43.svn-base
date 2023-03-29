package com.softigress.magicsigns.UI._base.Groups.Windows;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Keep;
import com.softigress.magicsigns.UI._base.Groups.Dialogs.DialogItem;
import com.softigress.magicsigns._Base._Drawing.DrawingGroup;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.AnimUtil;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;

import static com.softigress.magicsigns.UI._base.Groups.Windows.WindowMoveStyle.SIZEON;

public class GrpWindow extends DrawingGroup {

    private final float maxFx, maxFy, maxFw, maxFh;
    private float fx, fy, fw, fh;
    private float x, y, w, h;

    private static final int backMaxAlpha = 196;
    private int backAlpha = 0;
    private final Paint paintBack;

    protected int alpha;
    private static final int maxAlpha = 255;
    private static final int maxAlpha0 = 255;
    private final Paint paintWindow0;
    private final Paint paintWindow1;

    protected GrpWindow(float fw, float fh) {
        float ffw = fw * MetrixUtils.screen_K_default / MetrixUtils.screen_K;
        this.maxFw = ffw;
        this.maxFh = fh;
        this.maxFx = (1f - ffw) / 2f;
        this.maxFy = (1f - fh) / 3f;

        showDuration = CurrentSettings.windowShowDuration;
        hideDuration = CurrentSettings.windowHideDuration;

        paintBack = PaintUtils.getPaintBlack(backAlpha);
        paintWindow0 = PaintUtils.getPaintStroke(maxAlpha0, 32,32,32,2 * PaintUtils.strokeWidth);//getPaintStrokeWhite(maxAlpha0, 4 * PaintUtils.strokeWidth);
        paintWindow1 = PaintUtils.getPaint(maxAlpha, 12, 12, 12);
        setAlpha(maxAlpha);
    }

    @Keep
    public void setFx(float fx) { this.fx = fx; }
    public float getFx() { return this.fx; }
    @Keep
    public void setFy(float fy) { this.fy = fy; }
    public float getFy() { return this.fy; }
    public void setPoint(float fx, float fy) { this.fx = fx; this.fy = fy; }
    @Keep
    public void setFw(float fw) { this.fw = fw; }
    public float getFw() { return this.fw; }
    public float getW() { return w; }
    @Keep
    public void setFh(float fh) { this.fh = fh; }
    public float getFh() { return this.fh; }
    public void setSize(float fw, float fh) { this.fw = fw; this.fh = fh; }
    @Keep
    public void setAlpha(int a) {
        this.alpha = a;
        if (paintWindow0 != null)
            paintWindow0.setAlpha((int)(maxAlpha0 * a / (float)maxAlpha));
        if (paintWindow1 != null)
            paintWindow1.setAlpha(a); // / 4
    }
    public int getAlpha() { return this.alpha; }
    @Keep
    public void setBackAlpha(int a) {
        this.backAlpha = a;
        if (paintBack != null)
            paintBack.setAlpha(a);
    }

    public boolean inBounds(float px, float py) { return px > x && px < x + w && py > y && py < y + h; }

    //region show hide
    public long show(WindowMoveStyle windowMoveStyle) {
        super.show();
        moveWindow(windowMoveStyle, showDuration);
        return showDuration;
    }

    public long hide(WindowMoveStyle windowMoveStyle) {
        moveWindow(windowMoveStyle, hideDuration);
        return super.hide();
    }

    private void prepareWindow(WindowMoveStyle windowMoveStyle) {
        if (windowMoveStyle ==  SIZEON)
            setSize(0f, 0f);
        else
            setSize(maxFw, maxFh);

        float k = .4f;
        float k0 = 1 - k;
        float k1 = 1 + k;
        switch (windowMoveStyle) {
            //region show
            case SIZEON:
                setPoint(.5f, .5f);
                setAlpha(0);
                setBackAlpha(0);
                break;
            case LEFT_CENTER:
                setPoint(-.1f - maxFw, maxFy);
                setAlpha(maxAlpha);
                setBackAlpha(0);
                break;
            case RIGHT_CENTER:
                setPoint(1.1f + maxFw, maxFy);
                setAlpha(maxAlpha);
                setBackAlpha(0);
                break;
            case DOWN_CENTER:
                setPoint(maxFx, 1.1f + maxFh);
                setAlpha(maxAlpha);
                setBackAlpha(0);
                break;
            case UP_CENTER:
                setPoint(maxFx, -.1f - maxFh);
                setAlpha(maxAlpha);
                setBackAlpha(0);
                break;
            case ALPHA_ON:
                setPoint(maxFx,   maxFy * k1);
                setAlpha(0);
                setBackAlpha(0);
            //endregion
            //region hide
            case SIZEOFF:
            case CENTER_LEFT:
            case CENTER_RIGHT:
            case CENTER_DOWN:
            case CENTER_UP:
            case ALPHA_OFF:
                setPoint(maxFx,  maxFy);
                setAlpha(maxAlpha);
                setBackAlpha(maxAlpha);
                break;
            //endregion
        }
    }

    private void moveWindow(WindowMoveStyle windowMoveStyle, long duration) {
        float k = .4f;
        float k0 = 1 - k;
        float k1 = 1 + k;
        prepareWindow(windowMoveStyle);
        switch (windowMoveStyle) {
            //region show
            case SIZEON:
                new AnimUtil()
                        .add(this, "fw", 0, maxFw * k1, maxFw)
                        .add(this, "fh", 0, maxFh * k1, maxFh)
                        .add(this, "fx", .5f, maxFx * k0, maxFx)
                        .add(this, "fy", .5f, maxFy * k0, maxFy)
                        .add(this, "alpha", 0, maxAlpha)
                        .add(this, "backAlpha", 0, backMaxAlpha)
                        .startAD(duration);
                break;
            case LEFT_CENTER:
                new AnimUtil()
                        .add(this, "fx", -.1f - maxFw, maxFx * k1, maxFx)
                        .add(this, "backAlpha", 0, backMaxAlpha)
                        //.add(this, "alpha", 0, maxAlpha)
                        .startAD(duration);
                break;
            case RIGHT_CENTER:
                new AnimUtil()
                        .add(this, "fx", 1.1f + maxFw, maxFx * k0, maxFx)
                        .add(this, "backAlpha", 0, backMaxAlpha)
                        //.add(this, "alpha", 0, maxAlpha)
                        .startAD(duration);
                break;
            case DOWN_CENTER:
                new AnimUtil()
                        .add(this, "fy", 1.1f + maxFh, maxFy * k0, maxFy)
                        .add(this, "backAlpha", 0, backMaxAlpha)
                        //.add(this, "alpha", 0, maxAlpha)
                        .startAD(duration);
                break;
            case UP_CENTER:
                new AnimUtil()
                        .add(this, "fy", -.1f - maxFh, maxFy * k1, maxFy)
                        .add(this, "backAlpha", 0, backMaxAlpha)
                        //.add(this, "alpha", 0, maxAlpha)
                        .startAD(duration);
                break;
            case ALPHA_ON:
                new AnimUtil()
                        .add(this, "fy", maxFy * k1, maxFy *  1.025f, maxFy)
                        .add(this, "backAlpha", 0, backMaxAlpha)
                        .add(this, "alpha", 0, maxAlpha)
                        .startAD(duration);
                break;
            //endregion
            //region hide
            case SIZEOFF:
                new AnimUtil()
                        .add(this, "fw", maxFw, maxFw * k1, 0)
                        .add(this, "fh", maxFh, maxFh * k1, 0)
                        .add(this, "fx", maxFx, maxFx  * k0, .5f)
                        .add(this, "fy", maxFy, maxFy * k0, .5f)
                        .add(this, "alpha", maxAlpha, 0)
                        .add(this, "backAlpha", backMaxAlpha, 0)
                        .startAD(duration);
                break;
            case CENTER_LEFT:
                new AnimUtil()
                        .add(this, "fx", maxFx, maxFx * k1, -.1f - maxFw)
                        .add(this, "backAlpha", backMaxAlpha, 0)
                        .add(this, "alpha", maxAlpha, 0)
                        .startAD(duration);
                break;
            case CENTER_RIGHT:
                new AnimUtil()
                        .add(this, "fx", maxFx, maxFx * k0, 1.1f + maxFw)
                        .add(this, "backAlpha", backMaxAlpha, 0)
                        //.add(this, "alpha", maxAlpha, 0)
                        .startAD(duration);
                break;
            case CENTER_DOWN:
                new AnimUtil()
                        .add(this, "fy", maxFy, maxFy * k0, 1.1f + maxFh)
                        .add(this, "backAlpha", backMaxAlpha, 0)
                        //.add(this, "alpha", maxAlpha, 0)
                        .startAD(duration);
                break;
            case CENTER_UP:
                new AnimUtil()
                        .add(this, "fy", maxFy, maxFy * k1, -.1f - maxFh)
                        .add(this, "backAlpha", backMaxAlpha, 0)
                        //.add(this, "alpha", maxAlpha, 0)
                        .startAD(duration);
                break;
            case ALPHA_OFF:
                new AnimUtil()
                        .add(this, "fy", maxFy, maxFy * 1.025f, maxFy * k1)
                        .add(this, "backAlpha", backMaxAlpha, 0)
                        .add(this, "alpha", maxAlpha, 0)
                        .startAD(duration);
                break;
            //endregion
        }
    }
    //endregion

    protected float getByDx(float dx) { return fx + dx * fw; }
    protected float getByDy(float dy) { return fy + dy * fh; }
    protected float getByDw(float dw) { return dw * fw; }
    protected float getByDh(float dh) { return dh * fh; }
    protected void setPointByDxDy(DialogItem item) {
        if (item != null) {
            if (item.rect != null) {
                item.rect.setPoint(getByDx(item.getDx()), getByDy(item.getDy()));
                item.rect.setSize(getByDw(item.getDw()), getByDh(item.getDh()));
            }
            else if (item.control != null)
                item.control.setPoint(getByDx(item.getDx()), getByDy(item.getDy()));
            else if (item.controlTouchable != null)
                item.controlTouchable.setPoint(getByDx(item.getDx()), getByDy(item.getDy()));
        }
    }

    @Override
    public void calc() {
        this.w = fw * MetrixUtils.screen_metrix_width;
        this.h = fh * MetrixUtils.screen_metrix_height;
        this.x = fx * MetrixUtils.screen_metrix_width;
        this.y = fy * MetrixUtils.screen_metrix_height;
        if (paintWindow0 != null)
            paintWindow0.setStrokeWidth(2 * PaintUtils.strokeWidth);
    }

    private RectF rect = new RectF();
    private void drawBackGround(Canvas c) {
        if (paintBack != null)
            c.drawPaint(paintBack);
        float r = w / 10f;
        rect.left = x;
        rect.top = y;
        rect.right = x + w;
        rect.bottom = y + h;
        c.drawRoundRect(rect, r, r, paintWindow1); // заполнение
        c.drawRoundRect(rect, r, r, paintWindow0); // контур
    }

    @Override
    public void drawFrame(Canvas c) {
        calc();
        drawBackGround(c);
        super.drawFrame(c);
    }

    @Override
    public void recycle() { }
}
