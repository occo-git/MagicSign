package com.softigress.magicsigns.UI._base.Effects.Trace;

import android.graphics.PointF;
import com.softigress.magicsigns._Base.IRecycle;

public class DrawingTracePoint implements IRecycle {

    public float fx, fy;
    public final long ticks;

    public DrawingTracePoint(PointF point, long ticks) {
        this.fx = point.x;
        this.fy = point.y;
        this.ticks = ticks;
    }

    public void recycle() { }
}
