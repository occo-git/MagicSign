package com.softigress.magicsigns._Base._Drawing._base;

import com.softigress.magicsigns.UI._base.Groups.Dialogs.IDialogItemControlTouchable;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDownUpListener;
import com.softigress.magicsigns._Base._Drawing._interfaces.IMoveListener;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchableListener;

public class DrawingBaseTouchable extends DrawingBase implements IDialogItemControlTouchable {

    protected boolean isOnTouch = false;
    private boolean isTouched = false;

    public DrawingBaseTouchable(float fd) {
        super(fd);
    }

    public DrawingBaseTouchable(float fx, float fy, float fd) {
        super(fx, fy, fd);
    }

    protected DrawingBaseTouchable(float fx, float fy, float fw, float fh) {
        super(fx, fy, fw, fh);
    }

    //region touch move
    private ITouchableListener touchListener;
    public void setListener(ITouchableListener l) { this.touchListener = l; }
    private IMoveListener moveListener;
    public void setListener(IMoveListener l) { this.moveListener = l; }
    private IDownUpListener downupListener;
    public void setListener(IDownUpListener l) { this.downupListener = l;}

    public boolean onTouch(int x, int y) {
        isIn = false;
        if (isVisible()) {
            if (inBounds(x, y)) {
                onTouch();
                return true;
            }
        }
        return false;
    }

    protected void onTouch() {
        setOnTouch(true);
        if (touchListener != null)
            touchListener.handelOnTouch(this);
        isTouched = true;
    }

    public boolean onTouchUp(int x, int y) {
        isIn = false;
        if (isVisible()) {
            if (inBounds(x, y)) {
                onTouchUp();
                return true;
            }
        }
        isTouched = false;
        return false;
    }

    protected void onTouchUp() {
        setOnTouch(false);
        if (touchListener != null)
            touchListener.handelOnTouchUp(this);
        if (isTouched && downupListener != null)
            downupListener.handelOnDownUp(this);
    }

    public void onMoveTo(int x, int y) {
        if (isVisible()) {
            if (inBounds(x, y))
                onMoveIn();
            else
                onMoveOut();
        }
    }

    private boolean isIn = false;
    protected void onMoveIn() {
        setOnTouch(true);
        if (!isIn) {
            isIn = true;
            if (moveListener != null)
                moveListener.handelOnMoveIn(this);
        }
    }

    protected void onMoveOut() {
        setOnTouch(false);
        isIn = false;
        if (moveListener != null)
            moveListener.handelOnMoveOut(this);
    }

    private void setOnTouch(boolean isOnTouch) {
        if (this.isOnTouch != isOnTouch) {
            this.isOnTouch = isOnTouch;
            onTouchChanged();
        }
    }
    protected void onTouchChanged() {}
    //endregion
}