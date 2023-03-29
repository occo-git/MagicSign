package com.softigress.magicsigns._Base._Drawing._interfaces;

import android.graphics.Canvas;

import com.softigress.magicsigns._Base.IRecycle;

public interface IDrawing extends IRecycle {
    int getLayer(); // для отображений в drawing group над остальными элементами
    void calc();
    void drawFrame(Canvas c);
}
