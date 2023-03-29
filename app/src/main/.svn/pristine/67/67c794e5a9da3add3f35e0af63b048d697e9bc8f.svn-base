package com.softigress.magicsigns.Game.Signs.SignHelper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.SystemClock;
import com.softigress.magicsigns.R;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._Base._Drawing._base.DrawingSimple;
import com.softigress.magicsigns._system.BitmapManager;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Settings.Infos.SignInfo;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class SignHelper extends DrawingSimple {

    private static final float fxSignHelper = .5f, fySignHelper = .38f, frSignHelper = .3f;
    private static final float strokeWidth = .05f;
    private static final float fdHand = .25f;

    private boolean isVisible = false;
    private boolean isEnabled = true;
    private int helpCount = CurrentSettings.signHelpCount;

    private static final int showDuration = 1000;
    private static final int showDelay = 500;//6000;
    private static final int hideDuration = 1000;
    private static final int handDuration = 3000;

    private SignInfo signInfo;
    private PointF[] basePoints;
    private static final int maxAlpha = 70;
    private final Paint sPaint;
    private final Path signPath = new Path();
    private final DrawingBase hand;

    public SignHelper() {
        this(null);
    }

    public SignHelper(BitmapManager bitmapManager) {
        super(frSignHelper);

        sPaint = PaintUtils.getPaintStrokeWhite(0, PaintUtils.strokeWidth);
        sPaint.setDither(true);                    // set the dither to true
        sPaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        sPaint.setStrokeJoin(Paint.Join.ROUND);

        hand = new DrawingBase(fdHand, Utils.getBitmap(R.string.bmp_hand, bitmapManager));
        hand.setPoint(fxSignHelper, fySignHelper);

        setAlpha(0);
        setPoint(fxSignHelper, fySignHelper);
    }

    public boolean isAllowShowSign() { return isEnabled && helpCount > 0 && !isVisible; }

    private boolean isOnShow = false;
    private long startShowTicks = 0;
    public void showSign(SignInfo si) {
        //if (isEnabled && helpCount > 0 && !isVisible) {
            if (signInfo == null || signInfo.id != si.id) {
                isOnShow = true;
                isOnHide = false;
                startShowTicks = SystemClock.elapsedRealtime();
                this.signInfo = si;
                this.basePoints = si.getBasePoints();
                calcHandPoints();
                this.isVisible = true;
                helpCount--;
            }
        //}
    }

    private int handPointsCount = 0;
    private PointF[] handPoints;
    private void calcHandPoints() {
        if (basePoints != null) { // && signInfo.baseAreas != null) {
            int stepCount = basePoints.length;
            handStepDuraiton = handDuration / stepCount;
            handPointsCount = stepCount;
            handPoints = new PointF[stepCount];
            int i = 0;
            for (PointF bp : basePoints)
                handPoints[i++] = new PointF(fx + (bp.x - .5f) * fr / MetrixUtils.screen_K, fy + (bp.y - .5f) * fr);
        }
    }

    private boolean isOnHide = false;
    private long startHideTicks = 0;
    private int startHideAlpha = 0;
    public void hideSign() {
        if (isVisible && !isOnHide) {
            isOnShow = false;
            isOnHide = true;
            startHideTicks = SystemClock.elapsedRealtime();
            startHideAlpha = alpha;
        }
    }

    public void hide() {
        isEnabled = false;
        hideSign();
    }

    public int getSignInfoId() { return signInfo != null ? signInfo.id : 0; }

    //region draw
    @Override
    public void calc() {
        super.calc();
        if (sPaint != null) {
            sPaint.setStrokeWidth(MetrixUtils.screen_metrix_height * strokeWidth);
            sPaint.setAlpha(alpha);
        }
        if (hand != null)
            hand.setAlpha(alpha);
    }

    private int handStepDuraiton = 0;
    private void nextHandStep(long delta) {
        if (delta >= 0) {
            long dd = delta % handDuration;
            int handStep = (int) (dd / handStepDuraiton);
            if (handStep >= 0 && handStep < handPointsCount) {
                if (handStep < handPointsCount - 1) {
                    float pDelta = (float) (dd % handStepDuraiton) / handStepDuraiton;
                    PointF p1 = handPoints[handStep];
                    PointF p2 = handPoints[handStep + 1];
                    if (p1 != null && p2 != null)
                        hand.setPoint(p1.x + (p2.x - p1.x) * pDelta, p1.y + (p2.y - p1.y) * pDelta);
                } else
                    hand.setPoint(handPoints[handStep]);
            }
        }
    }

    @Override
    public void drawFrame(Canvas c) {
        if (isVisible && basePoints != null) {
            long delta = 0;
            if (isOnHide) {
                delta = SystemClock.elapsedRealtime() - startHideTicks;
                alpha = (int)(startHideAlpha * (1f - (float)delta / hideDuration));
                if (alpha <= 0) {
                    isVisible = false;
                    isOnHide = false;
                    alpha = 0;
                }
            } else if (isOnShow) {
                delta = SystemClock.elapsedRealtime() - showDelay - startShowTicks;
                if (delta > showDuration)
                    isOnShow = false;
                else
                    alpha = (int)(maxAlpha * (delta > 0 ? (float)delta / showDuration : 0));
            }

            calc();
            PointF pbp = null;
            signPath.reset();
            for (PointF bp : basePoints) {
                if (pbp == null)
                    signPath.moveTo(x + (bp.x - .5f) * r, y + (bp.y - .5f) * r);
                else
                    signPath.lineTo(x + (bp.x - .5f) * r, y + (bp.y - .5f) * r);
                pbp = bp;
            }
            c.drawPath(signPath, sPaint);

            if (hand != null) {
                nextHandStep(SystemClock.elapsedRealtime() - showDelay - startShowTicks);
                hand.drawFrame(c);
            }
        }
    }
    //endregion

    @Override
    public void recycle() {
        super.recycle();

        if (hand != null)
            hand.recycle();
        //if (signPath != null)
            signPath.reset();
    }
}
