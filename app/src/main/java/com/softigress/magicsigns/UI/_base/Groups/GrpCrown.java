package com.softigress.magicsigns.UI._base.Groups;

import androidx.annotation.Keep;

import com.softigress.magicsigns.UI._base.Controls.Crown.DrawingCrown;
import com.softigress.magicsigns.UI._base.Effects.Glares.Glares;
import com.softigress.magicsigns._Base._Drawing.DrawingGroup;
import com.softigress.magicsigns._system.Utils.MetrixUtils;


public class GrpCrown extends DrawingGroup {

    private float fx, fy, fd;
    private final DrawingCrown crown;
    private final Glares glares;

    @Keep
    public void setScale(float scale) { if (crown != null) crown.setScale(scale); }
    @Keep
    public void setFy(float fy) {
        this.fy = fy;
        crown.setFy(fy);
    }
    public float getFy() { return fy; }

    public GrpCrown(float fx, float fy, float fd) {
        this.fx = fx;
        this.fy = fy;
        this.fd = fd;

        crown = new DrawingCrown(fx, fy, fd);
        addDrawing(crown);
        float fr = .5f * fd;
        glares = new Glares(fx, fy, fr, fr * MetrixUtils.screen_K, 3);
        addDrawing(glares);
    }

    public boolean isVisible() { return glares != null && glares.isVisible(); }

    // устанавливает статус crow в соответствии с переданным index (позиция в рейтинге)
    public boolean setIndex(int index) {
        boolean res = crown.setIndex(index);
        if (glares != null) {
            if (index < 4 && crown.scaleK > 0) {
                float ffr = .5f * fd * crown.scaleK;
                glares.setBounds(fx, fy, ffr, ffr * MetrixUtils.screen_K);
                glares.show();
            } else
                glares.hide();
        }
        return res;
    }

    @Override
    public long hide() {
        crown.hide();
        glares.hide();
        return super.hide();
    }
}
