package com.softigress.magicsigns.UI._base.Groups.Dialogs;

import android.graphics.Canvas;
import androidx.annotation.Keep;

import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingRect;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;

public class DialogItem implements IDrawing {

    private float dx;
    private float dy;
    private float dw;
    private float dh;
    public DrawingRect rect;
    public IDialogItemControl control;
    public IDialogItemControlTouchable controlTouchable;
    private boolean isHidden = false;

    public float getDx() { return dx; }
    public float getDy() { return dy; }
    public float getDw() { return dw; }
    public float getDh() { return dh; }

    @Keep
    public void setDx(float dx) { this.dx = dx; }
    @Keep
    public void setDy(float dy) { this.dy = dy; }

    public void setAlpha(int a) {
        if (rect != null)
            rect.setAlpha(a);
        if (control != null)
            control.setAlpha(a);
        if (controlTouchable != null)
            controlTouchable.setAlpha(a);
    }

    public DialogItem(float dx, float dy, float dw, float dh) {
        this.rect = new DrawingRect();
        this.dx = dx;
        this.dy = dy;
        this.dw = dw;
        this.dh = dh;
    }

    public DialogItem(IDialogItemControl control, float dx, float dy) {
        this.control = control;
        this.dx = dx;
        this.dy = dy;
    }

    public DialogItem(IDialogItemControlTouchable controlTouchable, float dx, float dy) {
        this.controlTouchable = controlTouchable;
        this.dx = dx;
        this.dy = dy;
    }

    public void setDxDyPoint(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setDxDySize(float dw, float dh) {
        this.dw = dw;
        this.dh = dh;
    }

    public boolean isHidden() { return isHidden; }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
        if (isHidden)
            hide();
        else
            show();
    }
    public void show() {
        if (!isHidden) {
            if (rect != null)
                rect.show();
            if (control != null)
                control.show();
            if (controlTouchable != null)
                controlTouchable.show();
        }
    }

    public void hide() {
        if (rect != null)
            rect.hide();
        if (control != null)
            control.hide();
        if (controlTouchable != null)
            controlTouchable.hide();
    }

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    public void calc() { }

    public void drawFrame(Canvas c) {
        if (rect != null)
            rect.drawFrame(c);
        if (control != null)
            control.drawFrame(c);
        if (controlTouchable != null)
            controlTouchable.drawFrame(c);
    }
    //endregion

    public void recycle() {
        if (rect != null)
            rect.recycle();
        if (control != null)
            control.recycle();
        if (controlTouchable != null)
            controlTouchable.recycle();

        rect = null;
        control = null;
        controlTouchable = null;
    }
}
