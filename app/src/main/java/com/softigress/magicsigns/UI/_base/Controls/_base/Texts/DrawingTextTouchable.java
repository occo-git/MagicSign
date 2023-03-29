package com.softigress.magicsigns.UI._base.Controls._base.Texts;

import com.softigress.magicsigns.UI._base.Groups.Dialogs.IDialogItemControlTouchable;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchableListener;

public class DrawingTextTouchable extends DrawingText implements IDialogItemControlTouchable {

    public DrawingTextTouchable(DrawingHAlign hAlign, float size) {
        super(hAlign, size);
    }

    private ITouchableListener listener;
    public void setListener(ITouchableListener l) { listener = l; }

    @Override
    public boolean onTouch(int x, int y) {
        if (isVisible && rect.contains(x, y)) {
            if (listener != null)
                listener.handelOnTouch(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchUp(int x, int y) {
        if (isVisible && rect.contains(x, y)) {
            if (listener != null)
                listener.handelOnTouchUp(this);
            return true;
        }
        return false;
    }

    @Override
    public void onMoveTo(int x, int y) { }
}
