package com.softigress.magicsigns._system.Utils;

import android.graphics.Paint;

public abstract class PaintUtils {

    public static int strokeWidth = 1;
    public static int strokeWidth2 = 1;
    public static int strokeWidthTextBack = 1;
    public static void apply(int w, int h) {
        strokeWidth = h / 250;
        strokeWidth2 = h / 500;
        strokeWidthTextBack = h / 600;
    }

    public static Paint getPaint(int color) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(color);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        return p;
    }

    public static Paint getPaint(int a, int r, int g, int b) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setARGB(a, r, g, b);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        return p;
    }

    public static Paint getPaintWhite(int alpha) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setARGB(alpha, 255, 255, 255);
        p.setTextAlign(Paint.Align.CENTER);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        return p;
    }

    public static Paint getPaintBlack(int alpha) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setARGB(alpha, 0, 0, 0);
        p.setTextAlign(Paint.Align.CENTER);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        return p;
    }

    public static Paint getPaintStroke(int a, int r, int g, int b, float w) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setARGB(a, r, g, b);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(w);
        return p;
    }

    public static Paint getPaintStrokeWhite(int alpha, float w) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setARGB(alpha, 255, 255, 255);
        p.setTextAlign(Paint.Align.CENTER);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(w);
        return p;
    }

    public static Paint getPaintStrokeBlack(int alpha, float w) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setARGB(alpha, 0, 0, 0);
        p.setTextAlign(Paint.Align.CENTER);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(w);
        return p;
    }
}
