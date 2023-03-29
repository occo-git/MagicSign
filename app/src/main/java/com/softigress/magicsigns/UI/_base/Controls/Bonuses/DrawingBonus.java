package com.softigress.magicsigns.UI._base.Controls.Bonuses;

import android.graphics.Canvas;
import androidx.annotation.Keep;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.Settings.Infos.SignInfos;
import com.softigress.magicsigns._system.Utils.AnimUtil;
import com.softigress.magicsigns._system.Utils.TaskUtils;
import com.softigress.magicsigns._system.Utils.TextUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class DrawingBonus implements IDrawing {

    private static final int showDuration = 3000;
    private static final float starFd = .15f;

    private boolean isVisible = false;
    private final Integer score;
    private DrawingText txtScore;
    private DrawingText txtTitle;
    private DrawingBase item;
    private DrawingBase itemBack;

    public DrawingBonus(BonusType type, int score, String title) {
        this.score = score;
        txtScore = new DrawingText(DrawingHAlign.CENTER, TextUtils.controls_game_bonus_score);
        txtScore.setVerticalAlign(DrawingVAlign.BOTTOM);
        txtScore.isPaintShadow = false;
        txtScore.setAlpha(0);
        txtScore.hide();
        txtScore.setText("+ " + this.score.toString());

        txtTitle = new DrawingText(DrawingHAlign.CENTER, TextUtils.controls_game_bonus_title);
        txtTitle.setVerticalAlign(DrawingVAlign.TOP);
        txtTitle.setTextBack(4f, 32, 0, 0, 0);
        txtTitle.setAlpha(0);
        txtTitle.hide();
        txtTitle.setText(title);

        int bitmapId = SignInfos.getBitmapIdByBonusType(type);
        item = new DrawingBase(starFd, bitmapId);
        item.setAlpha(0);
        item.hide();

        itemBack = new DrawingBase(starFd, R.string.bmp_halo_orange_in);
        itemBack.setAlpha(0);
        itemBack.hide();
    }

    @Keep
    public void setFy(float fy) {
        txtScore.setFy(fy);
        txtTitle.setFy(fy + starFd);
        item.setFy(fy + starFd / 2);
        //itemBack.setFy(fy + starFd / 2);
    }
    public void setPoint(float fx, float fy) {
        txtScore.setPoint(fx, fy);
        txtTitle.setPoint(fx, fy + starFd);
        item.setPoint(fx, fy + starFd / 2);
        itemBack.setPoint(fx, fy + starFd / 2);
    }

    public void startAnim(float fx, float fy) {
        isVisible = true;

        setPoint(fx, fy);
        txtScore.setFontSize(1f);
        item.setScale(0);
        itemBack.setScale(0);

        txtScore.show();
        txtTitle.show();
        item.show();
        itemBack.show();

        new AnimUtil()
                .addWithDuration(showDuration, txtScore, "fontSize", 1f, 1f, TextUtils.controls_game_cell_score, TextUtils.controls_game_cell_score)
                .addWithDuration(showDuration, item, "scale", 0f, 1.2f, .9f, 1f, 0f)
                .addWithDuration(showDuration / 6, itemBack, "scale", 0f, 2f)
                .addWithDuration(showDuration, txtScore, "alpha", 0, 255, 255, 255, 0)
                .addWithDuration(showDuration, txtTitle, "alpha", 0, 255, 255, 255, 0)
                .addWithDuration(showDuration, item, "alpha", 0, 255, 255, 255, 0)
                .addWithDuration(showDuration / 6, itemBack, "alpha", 0, 96, 48, 0)
                .startAD(showDuration);

        /*ObjectAnimator aSF = ObjectAnimator.ofFloat(txtScore, "fontSize", 1f, 1f, TextUtils.controls_game_cell_score, TextUtils.controls_game_cell_score).setDuration(showDuration);
        ObjectAnimator aIS = ObjectAnimator.ofFloat(item, "scale", 0f, 1.2f, .9f, 1f, 0f).setDuration(showDuration);
        ObjectAnimator aBS = ObjectAnimator.ofFloat(itemBack, "scale", 0f, 2f).setDuration(showDuration / 6);
        ObjectAnimator aSA = ObjectAnimator.ofInt(txtScore, "alpha", 0, 255, 255, 255, 0).setDuration(showDuration);
        ObjectAnimator aTA = ObjectAnimator.ofInt(txtTitle, "alpha", 0, 255, 255, 255, 0).setDuration(showDuration);
        ObjectAnimator aIA = ObjectAnimator.ofInt(item, "alpha", 0, 255, 255, 255, 0).setDuration(showDuration);
        ObjectAnimator aBA = ObjectAnimator.ofInt(itemBack, "alpha", 0, 96, 48, 0).setDuration(showDuration / 6);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(aSF, aIS, aBS, aSA, aIA, aTA, aBA);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();*/

        TaskUtils.postDelayed(showDuration, new Runnable() {
            @Override
            public void run() { isVisible = false; }
        });
        Utils.playSound(R.raw.bonus08, showDuration / 3);
    }

    /*public void startAnim(float fx, float fy) {
        isVisible = true;
        setPoint(fx, 1.3f);
        txtScore.show();
        txtTitle.show();
        item.show();
        starBack.show();

        ObjectAnimator aY = ObjectAnimator.ofFloat(this, "fy", 1.3f, fy * 0.8f, fy).setDuration(showDuration);
        ObjectAnimator tAs = ObjectAnimator.ofInt(txtScore, "alpha", 0, 255, 255, 255, 0).setDuration(showDuration);
        ObjectAnimator tFS = ObjectAnimator.ofFloat(txtScore, "fontSize", 1f, 1f, TextUtils.controls_game_cell_score, TextUtils.controls_game_cell_score).setDuration(showDuration);
        ObjectAnimator tA = ObjectAnimator.ofInt(txtTitle, "alpha", 0, 255, 255, 255, 0).setDuration(showDuration);
        ObjectAnimator sA = ObjectAnimator.ofInt(item, "alpha", 0, 255, 255, 255, 0).setDuration(showDuration);
        ObjectAnimator sbD = ObjectAnimator.ofFloat(itemBack, "fd", 0, starFd, starFd * 3).setDuration(showDuration);
        ObjectAnimator sbA = ObjectAnimator.ofInt(itemBack, "alpha", 0, 64, 64, 0).setDuration(showDuration);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(aY, tAs, tFS, tA, sA, sbD, sbA);
        //set.setInterpolator(new AccelerateInterpolator());
        set.setInterpolator(new DecelerateInterpolator());
        set.start();
        TaskUtils.postDelayed(showDuration, new Runnable() {
            @Override
            public void run() { isVisible = false; }
        });
        Utils.playSound(R.raw.bonus08, showDuration / 3);
    }*/

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    @Override
    public void calc() { }

    @Override
    public void drawFrame(Canvas c) {
        if (isVisible) {
            itemBack.drawFrame(c);
            item.drawFrame(c);
            txtScore.drawFrame(c);
            txtTitle.drawFrame(c);
        }
    }
    //endregion

    @Override
    public void recycle() {
        if (txtScore != null)
            txtScore.recycle();
        if (txtTitle != null)
            txtTitle.recycle();
        if (item != null)
            item.recycle();
        if (itemBack != null)
            itemBack.recycle();
        txtScore = null;
        txtTitle = null;
        item = null;
        itemBack = null;
    }
}
