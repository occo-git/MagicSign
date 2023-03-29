package com.softigress.magicsigns.UI._base.Grounds;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.SystemClock;

import com.softigress.magicsigns.UI._base.Effects.BackStars.BackStars;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.BitmapManager;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;

public class GroundBase implements IDrawing {

    private GroundFitType fitType;
    private final int id;
    private int width;
    private int height;
    private int bitmapWidth;
    private int bitmapHeight;
    private float k = 1;
    private Matrix m1 = new Matrix();
    private Bitmap bitmap;
    private Paint paint;
    private final Paint paintTest;
    private Paint maskPaint;
    private Matrix maskMatrix;

    private final boolean isStars;
    private final boolean isStarsMoving;
    private BackStars backStars;

    public GroundBase(int id, GroundFitType fitType, boolean isStars, boolean isStarsMoving) {
        this.id = id;
        this.fitType = fitType;
        this.paint = BitmapManager.getBitmapPaint();
        this.paint.setDither(true);
        this.paint.setFilterBitmap(false);
        this.paintTest = PaintUtils.getPaintWhite(255);
        this.paintTest.setTextSize(30);

        this.isStars = isStars;
        this.isStarsMoving = isStarsMoving;
        calc();
    }

    private void initStars() {
        if (this.width > 0 && this.height > 0) {
            if (isStars) {
                if (backStars != null)
                    backStars.recycle();
                backStars = new BackStars(this.width, this.height, CurrentSettings.groundStarsCount);
                backStars.isMoving = isStarsMoving;
                backStars.refresh();
            }
        }
    }

    public void refresh() {
        if (this.backStars != null)
            this.backStars.refresh();
    }

    // взять часть фона по маске
    public Bitmap getBitmapByMask(int x, int y, Bitmap mask) {
        if (this.bitmap != null) {
            final Bitmap output = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            maskPaint = BitmapManager.getBitmapPaint();
            canvas.drawBitmap(mask, 0, 0, maskPaint);

            maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            maskMatrix = new Matrix();
            maskMatrix.preConcat(m1);
            maskMatrix.postTranslate(-x, -y);
            canvas.drawBitmap(this.bitmap, maskMatrix, maskPaint);

            return output;
        }
        else
            return null;
    }

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    @Override
    public void calc() {
        if (this.width != MetrixUtils.screen_metrix_width || this.height != MetrixUtils.screen_metrix_height) {
            this.width = MetrixUtils.screen_metrix_width;
            this.height = MetrixUtils.screen_metrix_height;
            initStars();
            if (id > 0) {
                //region bitmap
                if (fitType == GroundFitType.BOTH) {
                    this.bitmap = BitmapManager.GetBitmap(id, width, height);//, Bitmap.Config.ALPHA_8);
                    //this.bitmap = BitmapManager.blurBitmap(this.bitmap);
                    if (bitmap != null) {
                        this.bitmapWidth = this.width;
                        this.bitmapHeight = this.height;
                        m1.setScale((float) this.width / this.bitmap.getWidth(), (float) this.height / this.bitmap.getHeight());
                    }
                } else if (fitType == GroundFitType.ONE) {
                    this.bitmap = BitmapManager.GetBitmap(id, width, height);//, Bitmap.Config.ALPHA_8);
                    //this.bitmap = BitmapManager.blurBitmap(this.bitmap);
                    if (bitmap != null) {
                        float bW = this.bitmap.getWidth();
                        float bH = this.bitmap.getHeight();
                        this.bitmapWidth = this.width;
                        this.bitmapHeight = this.height;
                        float kW = (float) this.width / bW;
                        float kH = (float) this.height / bH;
                        if (kW > kH) {
                            this.k = kW;
                            this.bitmapHeight = (int) (k * bH);
                        } else {
                            this.k = kH;
                            this.bitmapWidth = (int) (k * bW);
                        }
                        m1.setScale(k, k);
                        m1.postTranslate((this.width - this.bitmapWidth) / 2f, (this.height - this.bitmapHeight) / 2f); // выровнять
                    }
                } else if (fitType == GroundFitType.NONE) {
                    this.bitmap = BitmapManager.GetBitmap(id);//, Bitmap.Config.ALPHA_8);
                    //this.bitmap = BitmapUtils.blurBitmap(this.bitmap);
                    if (bitmap != null) {
                        this.bitmapWidth = this.bitmap.getWidth();
                        this.bitmapHeight = this.bitmap.getHeight();
                    }
                }
                //endregion
            }
        }
    }

    @Override
    public void drawFrame(Canvas c) {
        calc();

        if (bitmap != null) {
            if (fitType == GroundFitType.NONE)
                c.drawBitmap(bitmap, 0, 0, paint);
            else
                c.drawBitmap(bitmap, m1, paint);
        }
        else
            c.drawARGB(255, 0, 0, 0);
            //c.drawARGB(255, 32, 32, 32);

        if (backStars != null)
            backStars.drawFrame(c);

        //if (CurrentSettings.isDrawConnectionStatus && Utils.authManager != null)
        //    Utils.authManager.drawConnectionStatus(c);

        //if (CurrentSettings.isDrawFPS)
        //    drawINFO(c);
    }

    private String txtFPS;
    private int fps = 0;
    private long fpsTicks = 0;
    private void drawINFO(Canvas c) {
        // test FPS
        long ticks = SystemClock.elapsedRealtime();
        if (fpsTicks == 0) {
            fpsTicks = ticks;
            fps = 0;
        }
        long delta = ticks - fpsTicks;
        if (delta >= 1000) {
            txtFPS = String.format("FPS: %1$d", fps);
            fpsTicks = 0;
        } else {
            fps++;
        }
        if (txtFPS != null)
            c.drawText(txtFPS, this.width / 2, 50, paintTest);
        c.drawText(String.format("Mem: %1$d", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()), this.width / 2, 100, paintTest);

        // test//
        /*
        c.drawText(this.width + " " + this.height, 50, 50, paint);
        c.drawText(this.bitmapWidth + " " + this.bitmapHeight, 50, 100, paint);
        c.drawText(this.dx + " " + this.dy, 50, 150, paint);
        c.drawText((this.bitmapWidth - this.width) + " " + (this.bitmapHeight - this.height), 50, 200, paint);
        for (int i = 0; i < this.width; i += 100)
            c.drawLine(i, 0, i, 10, paint);
        for (int i = 0; i < this.height; i += 100)
            c.drawLine(0, i, 10, i, paint);
        c.drawLines( new float[] { 0, 0, this.width, 0, this.width, this.height, 0, this.height }, paint);
        c.drawCircle(this.width / 2, this.height / 2, 5, paint);//
         */

        //c.drawLine(0, .2f * MetrixUtils.screen_metrix_height, MetrixUtils.screen_metrix_width, .2f * MetrixUtils.screen_metrix_height, PaintUtils.getPaintStrokeWhite(255, PaintUtils.getStrokeWidth()));
        //c.drawARGB(64, 128, 255, 128);
        //c.drawARGB(32, 32, 32, 255);
        //c.drawARGB(64, 255, 128, 128);
    }
    //endregion

    public void recycle() {
        if (bitmap != null)
            bitmap.recycle();
        if (backStars != null)
            backStars.recycle();

        bitmap = null;
        backStars = null;
        m1 = null;
        paint = null;
        maskPaint = null;
        maskMatrix = null;
    }
}

