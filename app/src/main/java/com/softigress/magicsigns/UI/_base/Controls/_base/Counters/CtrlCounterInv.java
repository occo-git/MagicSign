package com.softigress.magicsigns.UI._base.Controls._base.Counters;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.MessageInfo;
//import com.softigress.magicsigns._Base._Drawing.DrawingFrameRate;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBaseTouchable;
import com.softigress.magicsigns._system.Utils.AnimUtil;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.TaskUtils;
import com.softigress.magicsigns._system.Utils.TextUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class CtrlCounterInv extends DrawingBaseTouchable {

    public static final float invFx = .82f, invFy = .83f;
    private static final float invFd =  .1f;
    private static final long addInvDuration = 1000;
    private static final long useInvDuration = 500;

    private final DrawingBase invSpot;
    private Integer invsCount = 0;
    private final DrawingText txtInvsCount;
    private final DrawingBase invHalo;

    private static final int maxPaintAlpha = 32;
    private final Paint paint1;

    //private DrawingFrameRate frameRate;

    private static final MessageInfo[] mis = MessageInfo.getMessages(
            "counterInv",
            new int[] {
                    0, 0, R.string.message_game_dna_bomb_01,
                    1, 0, R.string.message_game_dna_bomb_02,
                    1, 1, R.string.message_game_dna_bomb_03,
                    1, 0, R.string.message_game_dna_bomb_04,
            });
    public static MessageInfo getMessage() { return MessageInfo.getRandomMessage(mis); }

    public CtrlCounterInv() {
        super(invFx, invFy, invFd);

        invHalo = new DrawingBase(fd, R.string.bmp_halo_orange_in);
        invHalo.setPoint(fx, fy);
        invHalo.setAlpha(0);
        invHalo.hide();

        invSpot = new DrawingBase(fd, R.string.bmp_spot_orange);
        invSpot.setPoint(fx, fy);
        invSpot.setAlpha(0);

        txtInvsCount = new DrawingText(DrawingHAlign.CENTER, TextUtils.controls_inv_count);
        txtInvsCount.setVerticalAlign(DrawingVAlign.CENTER);
        txtInvsCount.setTextBack(4f, 32, 0, 0, 0);
        txtInvsCount.setAlpha(0);

        paint1 = PaintUtils.getPaintStrokeWhite(maxPaintAlpha, PaintUtils.strokeWidth);

        //frameRate = new DrawingFrameRate("inv counter", .04f, .35f);
    }

    public void addInventory() {
        invsCount++;
        if (invSpot != null) {
            invSpot.setAlpha(200);
            if (invsCount == 1) {
                ObjectAnimator a = ObjectAnimator.ofFloat(invSpot, "fd", 0f, 1.25f * fd, fd).setDuration(addInvDuration);
                a.setInterpolator(new AccelerateDecelerateInterpolator());
                a.start();
            } else {
                ObjectAnimator a = ObjectAnimator.ofFloat(invSpot, "fd", fd, 1.25f * fd, fd).setDuration(addInvDuration);
                a.setInterpolator(new AccelerateDecelerateInterpolator());
                a.start();
            }
            startBackAnim(true);
        }
        Utils.playSound(R.raw.inv_pick11);
        updateControls();
    }

    public boolean canUseInventory() { return invsCount > 0; }

    public boolean useInventory() {
        boolean res = false;
        if (invsCount > 0) {
            if (invSpot != null) {
                if (invsCount == 1)
                    ObjectAnimator.ofFloat(invSpot, "fd", this.fd, this.fd * .9f, 0f).setDuration(useInvDuration).start();
                else
                    ObjectAnimator.ofFloat(invSpot, "fd", this.fd, this.fd * .9f, this.fd).setDuration(useInvDuration).start();
            }
            invsCount--;
            res = true;
        }
        updateControls();
        return res;
    }

    private void updateControls() {
        if (txtInvsCount != null) {
            if (invsCount > 0) {
                txtInvsCount.setText(invsCount.toString());
                txtInvsCount.setAlpha(255);
            } else {
                txtInvsCount.setText("");
                txtInvsCount.setAlpha(0);
            }
        }
    }

    public void startBackAnim(boolean isInOut) {
        if (invHalo != null) {
            long duration = addInvDuration;
            invHalo.setAlpha(0);
            invHalo.show();

            new AnimUtil()
                    .add(invHalo, "Alpha", 0, 128, 0)
                    .add(invHalo, "scale", isInOut ? 3f : 1f, isInOut ? 1f : 5f)
                    .startAD(duration);

            /*Animator aA1 = ObjectAnimator.ofInt(invHalo, "Alpha", 0, 128, 0).setDuration(duration);
            Animator aS1 = ObjectAnimator.ofFloat(invHalo, "scale", isInOut ? 3f : 1f, isInOut ? 1f : 5f).setDuration(duration);
            AnimatorSet set = new AnimatorSet();
            set.setDuration(duration);
            set.playTogether(aA1, aS1);
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.start();*/
            TaskUtils.postDelayed(duration, new Runnable() { @Override public void run() { invHalo.hide(); } });
        }
    }

    //region draw
    @Override
    public void calc() {
        super.calc();

        if (invSpot != null)
            invSpot.setPoint(fx, fy);
        if (txtInvsCount != null)
            txtInvsCount.setPoint(fx, fy);
        if (invHalo != null)
            invHalo.setPoint(fx, fy);
        //if (paint1 != null)
        //    paint1.setAlpha(maxPaintAlpha * alpha / 255);
    }

    @Override
    public void drawBackground(Canvas c) {
        //if (frameRate != null)
        //    frameRate.start();

        super.drawBackground(c);

        if (invHalo != null)
            invHalo.drawFrame(c);
        if (invSpot != null)
            invSpot.drawFrame(c);
        if (txtInvsCount != null)
            txtInvsCount.drawFrame(c);
        if (paint1 != null) {
            if (isOnTouch)
                c.drawCircle(x, y, .475f * h, paint1);
            else
                c.drawCircle(x, y, .5f * h, paint1);
        }
        //if (frameRate != null)
        //    frameRate.drawFrame(c);
    }
    //endregion

    @Override
    public void recycle() {
        super.recycle();

        if (invHalo != null)
            invHalo.recycle();
        if (invSpot != null)
            invSpot.recycle();
        if (txtInvsCount != null)
            txtInvsCount.recycle();
        //if (frameRate != null)
        //    frameRate.recycle();
    }
}
