package com.softigress.magicsigns._Base._Drawing.Bezier;

import android.graphics.PointF;

import java.util.ArrayList;

public class DrawingBezierPath {

    private long duration = 0;
    private ArrayList<PointF> points = new ArrayList<>();

    public DrawingBezierPath() { }

    public DrawingBezierPath(float fx0, float fy0, float fx1, float fy1, float fx2, float fy2) {
        addPoint(fx0, fy0);
        addPoint(fx1, fy1);
        addPoint(fx2, fy2);
    }

    private void addPoint(float fx, float fy) { points.add(new PointF(fx, fy)); }

    private int stepsCount = 0;
    private long stepDuration = 0;
    private int currentStep = 0;
    private long startTicks = 0;
    private PointF p0, p1, p2;
    public void start(long duration) {
        this.duration = duration;
        stepsCount = points.size() - 2;
        stepDuration = stepsCount > 0 ? duration / stepsCount : 0;
        startTicks = 0;
    }

    private boolean nexPoints() {
        /*if (currentStep < stepsCount) {
            p0 = points.get(currentStep);
            p1 = points.get(currentStep + 1);
            p2 = points.get(currentStep + 2);
            return true;
        } else {
            p0 = null;
            p1 = null;
            p2 = null;
            return false;
        }*/

        if (currentStep > stepsCount - 1)
            currentStep = stepsCount - 1;

        p0 = points.get(currentStep);
        p1 = points.get(currentStep + 1);
        p2 = points.get(currentStep + 2);
        return p0 != null && p1 != null && p2 != null;
    }

    public PointF getCurrentPoint(long ticks) {
        if (stepsCount > 0 && currentStep < stepsCount && stepDuration > 0) {
            if (startTicks == 0)
                startTicks = ticks;
            long delta = ticks - startTicks;
            if (delta < 0)
                delta = 0;
            if (delta < duration) {
                if (stepDuration > 0) {
                    currentStep = (int) (delta / stepDuration);
                    long stepDelta = delta % stepDuration;
                    if (nexPoints()) {
                        float t = (float) stepDelta / stepDuration;
                        float t2 = t * t;
                        float t1 = 1 - t;
                        float t12 = t1 * t1;
                        return new PointF(
                                t12 * p0.x + 2 * t1 * t * p1.x + t2 * p2.x,
                                t12 * p0.y + 2 * t1 * t * p1.y + t2 * p2.y);
                    }
                } else
                    currentStep = 0;
            }
        }
        return null;
    }

    public void recycle() {
        if (points != null)
            points.clear();
        points = null;
    }
}
