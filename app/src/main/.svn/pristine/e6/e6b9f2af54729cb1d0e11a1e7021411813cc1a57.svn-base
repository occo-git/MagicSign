package com.softigress.magicsigns.UI._base.Controls.Lightning;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Keep;

import com.softigress.magicsigns.Game.Signs._base.DrawingSignCell;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.TaskUtils;
import com.softigress.magicsigns._system.Utils.Utils;

import java.util.Random;

public class DrawingLightning implements IDrawing {

    private boolean isVisible = false;

    private float x1, y1, x2, y2;
    private float dx, dy, len, dxl, dyl;
    private float fd = .01f;
    private float dLen;
    private int pointCount;
    private Paint paint1, paint2;
    private final int maxAlphaPaint1 = 60;
    private final int maxAlphaPaint2 = 30;
    private Path path = new Path();
    private final Random random;

    public DrawingLightning(float fd, float strokeWidth1, float strokeWidth2) {
        this.fd = fd;
        paint1 = PaintUtils.getPaintStrokeWhite(maxAlphaPaint1, strokeWidth1);
        paint2 = PaintUtils.getPaintStrokeWhite(maxAlphaPaint2, strokeWidth2);
        random = new Random();
    }

    public void start(int duration) {
        isVisible = true;
        Utils.playSoundLightning();
        TaskUtils.postDelayed(duration, new Runnable() { @Override public void run() { isVisible = false; } });
    }

    public boolean isVisible() { return isVisible; }
    public void show() { isVisible = true; }
    public void hide() { isVisible = false; }

    @Keep
    public void setAlpha(int alpha) {
        if (paint1 != null)
            paint1.setAlpha(maxAlphaPaint1 * alpha / 255);
        if (paint2 != null)
            paint2.setAlpha(maxAlphaPaint2 * alpha / 255);
    }

    public void setPath(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.dx = x2 - x1;
        this.dy = y2 - y1;
        this.len = (int)Math.sqrt(dx * dx + dy * dy);
        this.dxl = dx / len;
        this.dyl = dy / len;
        this.dLen = fd * MetrixUtils.screen_metrix_width;
        int pointCount = (int)(len / dLen);
        if (this.pointCount != pointCount) {
            this.pointCount = pointCount;
            pathPointX = new float[pointCount + 2];
            pathPointY = new float[pointCount + 2];
        }
    }

    public void setPathK(float cx, float cy, double angel) {
        float cos = (float) Math.cos(angel);
        if (cos != 0) {
            float sin = (float) Math.sin(angel);
            float k = sin / cos;
            PointF px_0 = new PointF(0, - k * cx + cy); // x = 0
            PointF px_w = new PointF(MetrixUtils.screen_metrix_width, k * (MetrixUtils.screen_metrix_width - cx) + cy); // x = w
            PointF py_0 = new PointF(cx - cy / k, 0); // y = 0
            PointF py_h = new PointF(cx + (MetrixUtils.screen_metrix_height - cy) / k, MetrixUtils.screen_metrix_height); // y = h

            PointF p1, p2;
            if (px_0.y < 0)
                p1 = py_0;
            else if (px_0.y > MetrixUtils.screen_metrix_height)
                p1 = py_h;
            else
                p1 = px_0;

            if (px_w.y < 0)
                p2 = py_0;
            else if (px_w.y > MetrixUtils.screen_metrix_height)
                p2 = py_h;
            else
                p2 = px_w;

            //if (p1 != null && p2 != null)
                setPath(p1.x, p1.y, p2.x, p2.y);

        } else
            setPath(cx, 0, cx, MetrixUtils.screen_metrix_height);
    }

    private float[] pathPointX;
    private float[] pathPointY;
    public boolean checkCollision(DrawingSignCell sign) {
        if (pathPointX != null && pathPointY != null && sign != null) {
            for (int i = 0; i < pointCount + 2; i++)
                if (sign.inRadius(pathPointX[i], pathPointY[i]))
                    return true;
        }
        return false;
    }

    public void clearPathPoints() {
        pathPointX = null;
        pathPointY = null;
    }

    /*private PointF[] pathPoints = null;
    public boolean checkCollision(DrawingSignCell sign) {
        if (pathPoints != null && sign != null)
            //synchronized (pathPoints) {
                for (PointF p : pathPoints)
                    if (p != null && sign.inRadius(p.x, p.y))
                        return true;
            //}
        return false;
    }

    public void clearPathPoints() {
        if (pathPoints != null)
            for (PointF p : pathPoints)
                p = null;
        pathPoints = null;
    }*/

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    public void calc() { }

    public void drawFrame(Canvas canvas) {
        if (pointCount > 0 && pathPointX != null && pathPointY != null) {
            //pathPoints = new PointF[pointCount + 2];
            //pathPointX = new float[pointCount + 2];
            //pathPointY = new float[pointCount + 2];
            if (isVisible) {
                path.reset();
                path.moveTo(x1, y1);
                int i = 0;
                //pathPoints[i++] = new PointF(x1, y1);
                i++;
                pathPointX[i] = x1;
                pathPointY[i] = y1;

                float rx = dxl * dLen;
                float ry = dyl * dLen;
                float l = dLen;
                while (l < len) {
                    //region ddx, ddy
                    float ddx = dxl * l;
                    float ddy = dyl * l;
                    int d1 = random.nextInt(10) + 1;
                    if (random.nextInt(2) > 0)
                        ddx += ry / d1;
                    else
                        ddx -= ry / d1;

                    int d2 = random.nextInt(10) + 1;
                    if (random.nextInt(2) > 0)
                        ddy += rx / d2;
                    else
                        ddy -= rx / d2;
                    //endregion

                    path.lineTo(x1 + ddx, y1 + ddy);
                    //pathPoints[i++] = new PointF(x1 + ddx, y1 + ddy);
                    i++;
                    pathPointX[i] = x1 + ddx;
                    pathPointY[i] = y1 + ddy;

                    l += dLen;
                }
                path.lineTo(x2, y2);
                //pathPoints[i] = new PointF(x2, y2);
                pathPointX[i] = x2;
                pathPointY[i] = y2;

                canvas.drawPath(path, paint1);
                canvas.drawPath(path, paint2);
            }
        }
    }
    //endregion

    public void recycle() {
        if (path != null)
            path.reset();
        clearPathPoints();

        paint1 = null;
        paint2 = null;
        path = null;
    }
}
