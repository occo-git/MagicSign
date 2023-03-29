package com.softigress.magicsigns._Base._Drawing._interfaces;

public interface ITouchable {
    boolean onTouch(int x, int y);
    boolean onTouchUp(int x, int y);
    void onMoveTo(int x, int y);
}
