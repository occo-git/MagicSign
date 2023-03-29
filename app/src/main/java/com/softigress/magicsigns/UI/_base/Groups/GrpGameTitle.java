package com.softigress.magicsigns.UI._base.Groups;

import androidx.annotation.Keep;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns._Base._Drawing.DrawingGroup;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;

public class GrpGameTitle extends DrawingGroup {

    private final DrawingBase titleBack;
    private final DrawingText txtTitle;

    public GrpGameTitle() {

        float backD = .3f;
        float xTitle =    .5f,    yTitle =    .1f;

        titleBack = new DrawingBase(xTitle, yTitle, backD);
        titleBack.setDefaultBitmap(R.string.bmp_spot_orange);
        titleBack.setScale(2f, .5f);
        titleBack.setAlpha(160);
        addDrawing(titleBack);

        txtTitle = new DrawingText(DrawingHAlign.CENTER, 40); // TextUtils.load_game_title
        txtTitle.setVerticalAlign(DrawingVAlign.CENTER);
        //txtTitle.setTextBack(8f, 64, 255, 255, 255);
        txtTitle.setTextBack(5f, 70, 128, 64, 0);
        txtTitle.setMultiLineInterval(.9f);
        txtTitle.setText(R.string.game_title);
        txtTitle.setPoint(xTitle, yTitle);
        addDrawing(txtTitle);
    }

    @Keep
    public void setAlpha(int a) {
        int aa = (int)(160f / 255f * a);
        titleBack.setAlpha(aa);
        txtTitle.setAlpha(a);
    }

    @Keep
    public void setPoint(float fx, float fy) {
        titleBack.setPoint(fx, fy);
        txtTitle.setPoint(fx, fy);
    }

    @Keep
    public void setFy(float fy) {
        titleBack.setFy(fy);
        txtTitle.setFy(fy);
    }

    @Override
    public void show() {
        super.show();
        titleBack.show();
        txtTitle.show();
    }

    @Override
    public long hide() {
        titleBack.hide();
        txtTitle.hide();
        return super.hide();
    }
}
