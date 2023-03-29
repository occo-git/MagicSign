package com.softigress.magicsigns.UI.Lab;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Controls.Achievements.AchievementType;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns.UI._base.Effects.Glares.Glares;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBaseTouchable;
import com.softigress.magicsigns._system.Settings.Infos.SignInfos;
import com.softigress.magicsigns._system.Utils.TextUtils;

public class LabItem extends DrawingBaseTouchable {

    public static final float fdItem = .1f;
    private static final float fdBack = .15f;
    private static final int indexDuration = 1000;
    private static final int backDuration = 1000;

    private final AchievementType type;
    private int bitmapId = 0;
    public Integer realIndex = null;
    private Integer currentIndex = null;
    private boolean isSelected = false;
    private boolean isEnabled = false;

    private final DrawingBase back;
    private final DrawingBase item;
    private final DrawingBase itemDisabled;
    private final DrawingText txtIndex;
    private final Glares glares;

    public LabItem(int realIndex, AchievementType type, float fx, float fy) {
        super(fdItem / 1.5f);

        this.type = type;
        this.bitmapId = SignInfos.getBitmapIdByAchievementType(type);
        this.realIndex = realIndex;

        back = new DrawingBase(fdBack, R.string.bmp_spot_white);
        back.setScale(0f);

        item = new DrawingBase(fdItem, bitmapId);
        item.hide();
        itemDisabled = new DrawingBase(fdItem, SignInfos.getBitmapIdByAchievementType(AchievementType.EMPTY_00));

        txtIndex = new DrawingText(DrawingHAlign.CENTER, TextUtils.lab_item_index);
        txtIndex.setVerticalAlign(DrawingVAlign.CENTER);
        txtIndex.setTextBack(8f, 32, 0, 0, 0);

        float fr = fdItem / 2f;
        glares = new Glares(fx, fy, fr, fr, fr / 4f, 1);

        setPoint(fx, fy);
    }

    public void setCurrentIndex(Integer index) {
        if (txtIndex != null) {
            // при присвоении того же индекса, сбрасываем индекс
            if (currentIndex == index)
                index = null;
            txtIndex.setText(index == null ? null : index.toString());
            if (index == null) {
                item.setAlpha(255);
                txtIndex.setAlpha(0);
            } else if (currentIndex == null) {
                item.setAlpha(0);
                txtIndex.setAlpha(255);
            }
            currentIndex = index;
            showhideGlares();
        }
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        if (isEnabled) {
            item.show();
            itemDisabled.hide();
        } else {
            item.hide();
            itemDisabled.show();
        }
        showhideGlares();
    }

    private void showhideGlares() {
        if (glares != null) {
            boolean showhide = (isEnabled && (currentIndex == null));
            if (showhide)
                glares.show();
            else
                glares.hide();
        }
    }

    public boolean isEnabled() { return isEnabled; }

    public Integer getCurrentIndex() { return currentIndex; }

    public boolean isCorrect() { return currentIndex == realIndex; }

    public AchievementType getAchievementType() { return type; }

    public void select(boolean isSelect) {
        if (isEnabled) {
            if (isSelected != isSelect) {
                isSelected = isSelect;
                if (isSelect)
                    ObjectAnimator.ofFloat(back, "scale", 0f, 1.2f, 1f).setDuration(backDuration).start();
                else
                    ObjectAnimator.ofFloat(back, "scale", 1f, 0f).setDuration(backDuration).start();
            }
        }
    }

    public boolean isSelected() { return isSelected; }

    @Override
    public void calc() {
        super.calc();
        float fr = fd / 2f;
        float fd5 = fd / 5f;

        if (back != null) {
            back.setAlpha(alpha / 3);
            back.setPoint(fx, fy + fd5);
        }

        if (currentIndex == null) {
            if (item != null && item.isVisible())
                item.setAlpha(alpha);
            if (itemDisabled != null && itemDisabled.isVisible())
                itemDisabled.setAlpha(alpha);
        } else if (txtIndex != null)
            txtIndex.setAlpha(alpha);

        if (item != null)
            item.setPoint(fx, fy);
         if (itemDisabled != null)
             itemDisabled.setPoint(fx, fy);
        if (txtIndex != null)
            txtIndex.setPoint(fx, fy + fd5);
        if (glares != null)
            glares.setBounds(fx, fy, fr, fr);
    }

    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);
        if (isVisible()) {
            if (back != null)
                back.drawFrame(c);
            if (item != null)
                item.drawFrame(c);
            if (itemDisabled != null)
                itemDisabled.drawFrame(c);
            if (txtIndex != null && txtIndex.hasText())
                txtIndex.drawFrame(c);
            if (glares != null)
                glares.drawFrame(c);
        }
    }

    @Override
    public void recycle() {
        super.recycle();
        if (back != null)
            back.recycle();
        if (item != null)
            item.recycle();
        if (txtIndex != null)
            txtIndex.recycle();
        if(glares != null)
            glares.recycle();
    }
}
