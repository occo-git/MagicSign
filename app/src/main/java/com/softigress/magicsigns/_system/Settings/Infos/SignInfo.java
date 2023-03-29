package com.softigress.magicsigns._system.Settings.Infos;

import android.graphics.PointF;

import com.softigress.magicsigns.Game.Puncher.RaySign;
import com.softigress.magicsigns._Base.ArrayRecyclableSimple;
import com.softigress.magicsigns._Base.IRecycle;
import com.softigress.magicsigns._system.Settings.CurrentSettings;

import java.util.ArrayList;

public class SignInfo implements IRecycle {

    public final int id;
    public final SignStrength strength;

    private float minX, minY, maxX, maxY;
    private final float sizeX, sizeY;
    private final ArrayList<SignArea> baseAreas = new ArrayList<>();
    private final ArrayRecyclableSimple<PointF> basePoints = new ArrayRecyclableSimple<>(PointF.class);
    private PointF[] basePointItems;
    private final ArrayRecyclableSimple<SignArea> pathAreas = new ArrayRecyclableSimple<>(SignArea.class);
    private SignArea[] pathAreaItems;

    public SignInfo(int id, float[] points, SignStrength strength) {
        this.id = id;
        this.strength = strength;

        for (int i = 0; i < points.length; i += 2)
            addBaseArea(points[i], points[i + 1]);

        // update info
        getMinMax();
        sizeX = maxX - minX;
        sizeY = maxY - minY;
    }

    private void addBaseArea(float x, float y) {
        float aR = CurrentSettings.signAreaR;
        SignArea prevArea = null;
        if (baseAreas.size() > 0)
            prevArea = baseAreas.get(baseAreas.size() - 1);
        SignArea newArea = new SignArea(x, y, aR);
        if (prevArea != null) {
            float px = prevArea.x;
            float py = prevArea.y;
            int sX = newArea.x < px ? -1: 1;
            int sY = newArea.y < py ? -1: 1;
            float deltaX = Math.abs(px - newArea.x);
            float deltaY = Math.abs(py - newArea.y);
            float dx = 0, dy = 0;
            int steps = 0;
            if (deltaX > deltaY) {
                dx = sX * aR;
                dy = sY * aR * deltaY / deltaX;
                steps = (int)(deltaX / aR);
            } else if (deltaX < deltaY) {
                dx = sX * aR * deltaX / deltaY;
                dy = sY * aR;
                steps = (int)(deltaY / aR);
            } else {
                dx = sX * aR;
                dy = sY * aR;
                steps = (int)(deltaX / aR);
            }
            for (int s = 0; s <= steps; s++) {
                pathAreas.add(new SignArea(px, py, aR));// * 1.5f));
                px += dx;
                py += dy;
            }
        } else
            pathAreas.add(newArea);

        pathAreaItems = pathAreas.getItems();
        baseAreas.add(newArea);
        basePoints.add(new PointF(newArea.x, newArea.y));
        basePointItems = basePoints.getItems();
    }

    public PointF[] getBasePoints() { return basePointItems; }

    private void getMinMax() {
        SignArea a0 = baseAreas.get(0);
        if (baseAreas.size() == 0 || a0 == null)
            return;
        float minX = a0.x;
        float minY = a0.y;
        float maxX = a0.x;
        float maxY = a0.y;
        for (SignArea a : baseAreas)
            if (a != null) {
                minX = Math.min(minX, a.x);
                minY = Math.min(minY, a.y);
                maxX = Math.max(maxX, a.x);
                maxY = Math.max(maxY, a.y);
            } else
                break;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    //region check
    public boolean check(RaySign raySign) {
        PointF[] points = raySign.getPoints(minX, minY, sizeX, sizeY);
        if (checkBaseAreas(points))
            return checkPathAreas(points);
        else
            return false;
    }

    private boolean checkBaseAreas(PointF[] points) {
        // проверяем, что в каждой базовой области содержится хотя бы одна точка
        if (baseAreas.size() > 0) {
            for (SignArea a : baseAreas)
                if (!a.inBoundsAny(points))
                    return false; // в базовой области "a" не содержится ни одна точка
            return true;
        } else
            return false;
    }

    private boolean checkPathAreas(PointF[] points) {
        // проверяем, что каждая точка содержится в контуре
        if (points.length > 0) {
            for (PointF p : points)
                if (!inPathArea(p))
                    return false;
            return true;
        } else
            return false;
    }

    // точка в контуре
    private boolean inPathArea(PointF p) {
        for (SignArea a : pathAreaItems)
            if (a.inBounds(p))
                return true;
        return false;
    }
    //endregion

    public void recycle() {
        //if (baseAreas != null)
            baseAreas.clear();
        //if (pathAreas != null)
            pathAreas.recycle();
    }
}
