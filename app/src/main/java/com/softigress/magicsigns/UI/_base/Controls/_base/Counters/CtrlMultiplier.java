package com.softigress.magicsigns.UI._base.Controls._base.Counters;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.Keep;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.MessageInfo;
import com.softigress.magicsigns.UI._base.Effects.Glares.Glares;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawingTouchable;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchableListener;
import com.softigress.magicsigns._system.Utils.AnimUtil;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.TaskUtils;
import com.softigress.magicsigns._system.Utils.TextUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class    CtrlMultiplier extends DrawingText implements IDrawingTouchable {

    public static final float mltFx = .9f;
    public static final float mltFy = .65f;
    private static final float mltFr = .04f;
    private static final int activateDuration = 2000;
    private static final int chargeDuration = 1000;
    private static final int inDuration = 1000;
    private static final int outDuration = 750;
    private float fr = mltFr, fr0 = mltFr;
    private float r, r0;
    private float percent = 0f;

    //private DrawingBase mltBack; // back
    private DrawingBase mltHaloIn; // halo in
    private DrawingBase mltHaloOut; // halo out
    private DrawingBase mltHaloCharge; // charge halo
    private Glares glares;
    private Paint progressPaint0;
    //private Paint progressPaint1;
    private Paint multiplierPaint;
    private Paint multiplierPaint1;

    private static final MessageInfo[] mis = MessageInfo.getMessages(
            "multiplier",
            new int[] {
                    0, 0, R.string.message_game_multiplier_01,
                    1, 0, R.string.message_game_multiplier_02,
                    1, 1, R.string.message_game_multiplier_03,
            });
    public static MessageInfo getMessage() { return MessageInfo.getRandomMessage(mis); }

    public CtrlMultiplier() {
        super(DrawingHAlign.CENTER, TextUtils.controls_multimplier);

        //mltBack = new DrawingBase(2f * fr, R.string.bmp_spot_white);
        //mltBack.setAlpha(0);
        //mltBack.hide();

        mltHaloIn = new DrawingBase(2f * fr, R.string.bmp_halo_white_in);
        mltHaloIn.setAlpha(0);
        mltHaloIn.hide();

        mltHaloOut = new DrawingBase(6f * fr, R.string.bmp_halo_white_out);
        mltHaloOut.setAlpha(0);
        mltHaloOut.hide();

        mltHaloCharge = new DrawingBase(2f * fr, R.string.bmp_glare_01);
        mltHaloCharge.setAlpha(0);
        mltHaloCharge.hide();

        glares = new Glares(fx, fy, 1.5f * fr, 1.5f * fr, .5f * fr, 1);
        glares.setNotInBounds(fr, fr);

        setVerticalAlign(DrawingVAlign.CENTER);
        setTextARGB(255, 255, 255, 255);
        setTextBack(4f, 32, 0, 0, 0);
        setPoint(mltFx + 8f * mltFr, mltFy);

        progressPaint0 = PaintUtils.getPaintWhite(256 / 4);
        //progressPaint1 = PaintUtils.getPaintStrokeWhite(256 / 8, PaintUtils.strokeWidth);
        multiplierPaint = PaintUtils.getPaintWhite(256 / 16);
        multiplierPaint1 = PaintUtils.getPaintStrokeWhite(256 / 8, PaintUtils.strokeWidth);
    }

    @Keep
    public void setFr(float fr) { this.fr = fr; }
    @Keep
    public void setFr0(float fr0) { this.fr0 = fr0; }

    private int multiplier = 0;
    public void setMultiplier(int multiplier) {

        // multiplier anim
        long duration = activateDuration;
        /*float fy0 = getFy();
        float fy1 = multiplier > 0 ? mltFy : mltFy + .5f;
        float dy = fy0 - fy1;
        Animator a0 = ObjectAnimator.ofFloat(this, "Fy", fy0, fy1 - .05f * dy, fy1).setDuration(2 * duration);
        a0.setInterpolator(new AccelerateDecelerateInterpolator());
        a0.start();*/
        float fx0 = getFx();
        float fx1 = multiplier > 0 ? mltFx : mltFx + 8f * mltFr;
        float dx = fx0 - fx1;
        Animator a0 = ObjectAnimator.ofFloat(this, "Fx", fx0, fx1 - .05f * dx, fx1).setDuration(duration);
        a0.setInterpolator(new AccelerateDecelerateInterpolator());
        a0.start();

        float rk = 1.2f;
        if (multiplier > 1) {
            float rk0 = multiplier > this.multiplier ? .62f : rk;
            float rk1 = multiplier > this.multiplier ? rk : .62f;
            setPercent(0);
            setText("*" + multiplier);
            Animator a = ObjectAnimator.ofFloat(this, "Fr", mltFr * rk0, mltFr * rk1, mltFr).setDuration(duration);
            a.setInterpolator(new AccelerateDecelerateInterpolator());
            a.start();
        }
        else if (multiplier == 1) {
            setPercent(0);
            setText(" ");
            Animator a = ObjectAnimator.ofFloat(this, "Fr", mltFr, mltFr * rk, 0).setDuration(duration);
            a.setInterpolator(new AccelerateDecelerateInterpolator());
            a.start();
        } else if (multiplier == 0) {
            Animator a = ObjectAnimator.ofFloat(this, "Fr0", mltFr, mltFr * rk, 0).setDuration(duration);
            a.setInterpolator(new AccelerateDecelerateInterpolator());
            a.start();
        }
        startMultiplyAnim(multiplier > this.multiplier);
        this.multiplier = multiplier;

        if (multiplier > 0)
            glares.show();
        else
            glares.hide();
    }

    public void setPercent(float percent) {
        if (percent < 0)
            percent = 0;
        if (percent > 1)
            percent = 1;
        if (percent > this.percent && fr0 > 0) {
            Animator a = ObjectAnimator.ofFloat(this, "Fr0", mltFr, mltFr * 1.2f, mltFr).setDuration(chargeDuration);
            a.setInterpolator(new AccelerateDecelerateInterpolator());
            a.start();
        }
        this.percent = percent;
    }

    @Override
    public void setAlpha(int a) {
        super.setAlpha(a);
        progressPaint0.setAlpha(a / 4);//(a * 32 / 256);
        //progressPaint1.setAlpha(a / 8);//(a * 16 / 256);
        multiplierPaint.setAlpha(a / 16);//(a * 8 / 256);
        multiplierPaint1.setAlpha(a / 8);//(a * 16 / 255);
    }

    private boolean isOnCharge = false;
    private AnimatorSet animCharge = null;
    public void startChargeAnim() {
        if (mltHaloCharge != null)
            if (!isOnCharge) {
                isOnCharge = true;
                long duration = chargeDuration;

                if (animCharge != null && animCharge.isStarted())
                    animCharge.cancel();

                mltHaloCharge.setPoint(fx, fy);
                mltHaloCharge.setAlpha(0);
                mltHaloCharge.show();

                animCharge = new AnimUtil()
                        .add(mltHaloCharge, "alpha", 32, 64, 0)
                        .add(mltHaloCharge, "scale", .3f, .95f, .5f)
                        .add(mltHaloCharge, "angel", 360 * Utils.getRandom(), 360 * Utils.getRandom())
                        .startA(duration);

                TaskUtils.postDelayed(duration, new Runnable() {
                    @Override
                    public void run() {
                        if (mltHaloCharge != null)
                            mltHaloCharge.hide();
                        isOnCharge = false;
                    }
                });
            }
    }
    private void startMultiplyAnim(boolean isUp) {
        if (isUp)
            startMultiplyAnimIn();
        else
            startMultiplyAnimOut();
    }

    private boolean isOnMultiplyIn = false;
    private void startMultiplyAnimIn() {
        if (mltHaloIn != null)
            if (!isOnMultiplyIn) {
                isOnMultiplyIn = true;
                long duration = inDuration;

                //mltBack.setPoint(fx, fy);
                //mltBack.setAlpha(0);
                //mltBack.show();
                mltHaloIn.setPoint(fx, fy);
                mltHaloIn.setAlpha(0);
                mltHaloIn.show();

                new AnimUtil()
                        .add(mltHaloIn, "Alpha", 0, 64, 0)
                        .add(mltHaloIn, "scale", 3f, 1f)
                        .start(duration);

                /*//Animator aBA = ObjectAnimator.ofInt(mltBack, "Alpha", 0, 64, 0).setDuration(duration);
                //Animator aBS = ObjectAnimator.ofFloat(mltBack, "scale", 0f, 2f, 3f).setDuration(duration);
                Animator aHA = ObjectAnimator.ofInt(mltHaloIn, "Alpha", 0, 64, 0).setDuration(duration);
                Animator aHS = ObjectAnimator.ofFloat(mltHaloIn, "scale", 3f, 1f).setDuration(duration);
                AnimatorSet set = new AnimatorSet();
                set.setDuration(duration);
                //set.playTogether(aBA, aBS, aHA, aHS);
                set.playTogether(aHA, aHS);
                set.start();*/
                TaskUtils.postDelayed(duration, new Runnable() {
                    @Override
                    public void run() {
                        //mltBack.hide();
                        if (mltHaloIn != null)
                            mltHaloIn.hide();
                        isOnMultiplyIn = false;
                    }
                });
            }
    }

    private boolean isOnMultiplyOut = false;
    private void startMultiplyAnimOut() {
        if (mltHaloOut != null)
            if (!isOnMultiplyOut) {
                isOnMultiplyOut = true;
                long duration = outDuration;

                mltHaloOut.setPoint(fx, fy);
                mltHaloOut.setAlpha(0);
                mltHaloOut.show();

                new AnimUtil()
                        .add(mltHaloOut, "Alpha", 0, 64, 0)
                        .add(mltHaloOut, "scale", .25f, 1f)
                        .startAD(duration);

                /*Animator aA1 = ObjectAnimator.ofInt(mltHaloOut, "Alpha", 0, 64, 0).setDuration(duration);
                Animator aS = ObjectAnimator.ofFloat(mltHaloOut, "scale", .25f, 1f).setDuration(duration);
                AnimatorSet set = new AnimatorSet();
                set.setDuration(duration);
                set.playTogether(aA1, aS);
                set.setInterpolator(new AccelerateDecelerateInterpolator());
                set.start();*/
                TaskUtils.postDelayed(duration, new Runnable() {
                    @Override
                    public void run() {
                        if (mltHaloOut != null)
                            mltHaloOut.hide();
                        isOnMultiplyOut = false;
                    }
                });
            }
    }

    //region Touch
    private ITouchableListener touchListener;
    public void setListener(ITouchableListener l) {
        this.touchListener = l;
    }

    public boolean onTouch(int x, int y) {
        if (isVisible) {
            if (inBounds(x, y)) {
                if (touchListener != null)
                    touchListener.handelOnTouch(this);
                return true;
            }
        }
        return false;
    }

    public boolean onTouchUp(int x, int y) {
        if (isVisible) {
            if (inBounds(x, y)) {
                if (touchListener != null)
                    touchListener.handelOnTouchUp(this);
                return true;
            }
        }
        return false;
    }

    private boolean inBounds(float px, float py) { return x - r0 <= px && px <= x + r0 && y - r0 <= py && py <= y + r0; }

    public void onMoveTo(int x, int y) { }
    //endregion

    @Override
    public void calc() {
        super.calc();
        r = fr * MetrixUtils.screen_metrix_height;
        r0 = fr0 * MetrixUtils.screen_metrix_height;
        //if (mltBack != null)
        //    mltBack.setPoint(fx, fy);
        if (mltHaloIn != null)
            mltHaloIn.setPoint(fx, fy);
        if (mltHaloOut != null)
            mltHaloOut.setPoint(fx, fy);
        if (mltHaloCharge != null)
            mltHaloCharge.setPoint(fx, fy);
        if (glares != null) {
            glares.setBounds(fx, fy, 1.5f * fr, 1.5f * fr);
            glares.setNotInBounds(fr, fr);
        }
    }

    private RectF rect = new RectF();
    @Override
    protected void drawBackground(Canvas c) {

        float dr = r0 * .8f;
        if (isOnMultiplyIn) {
            //if (mltBack != null)
            //    mltBack.drawFrame(c);
            if (mltHaloIn != null)
                mltHaloIn.drawFrame(c);
        }
        if (isOnMultiplyOut && mltHaloOut != null)
            mltHaloOut.drawFrame(c);
        if (isOnCharge && mltHaloCharge != null)
            mltHaloCharge.drawFrame(c);
        // percent
        rect.left = x - dr;
        rect.top = y - dr;
        rect.right = x + dr;
        rect.bottom = y + dr;
        c.drawArc(rect,
                360 * (1 - this.percent) - 45,
                360 * this.percent,
                true, progressPaint0);
        // circle
        c.drawCircle(x, y, r0, multiplierPaint1); // r0, progressPaint1
        // fill
        c.drawCircle(x, y, r, multiplierPaint);

        if (glares != null)
            glares.drawFrame(c);
    }

    @Override
    public void recycle() {
        super.recycle();

        //if (mltBack != null)
        //    mltBack.recycle();
        if (mltHaloIn != null)
            mltHaloIn.recycle();
        if (mltHaloOut != null)
            mltHaloOut.recycle();
        if (mltHaloCharge != null)
            mltHaloCharge.recycle();
        if (glares != null)
            glares.recycle();

        //mltBack = null;
        mltHaloIn = null;
        mltHaloOut = null;
        mltHaloCharge = null;
        progressPaint0 = null;
        //progressPaint1 = null;
        multiplierPaint = null;
        multiplierPaint1 = null;
        glares = null;
    }
}
