package com.softigress.magicsigns.UI._base.Controls.Crown;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;

public class DrawingCrownSmall extends DrawingBase {

    private static final float crownK_gold = 1f;
    private static final float crownK_silver = .95f;
    private static final float crownK_bronze = .9f;

    public DrawingCrownSmall(float fx, float fy, float fd) {
        super(fx, fy, fd);

        setStatusBitmap(DrawingCrown.CROWN_GOLD, R.string.bmp_crown_01_gold_s);
        setStatusBitmap(DrawingCrown.CROWN_SILVER, R.string.bmp_crown_02_silver_s);
        setStatusBitmap(DrawingCrown.CROWN_BRONZE, R.string.bmp_crown_03_bronze_s);
    }

    @Override
    public void setStatus(int statusId) {
        super.setStatus(statusId);
        switch (statusId) {
            case DrawingCrown.CROWN_GOLD: setScale(crownK_gold); break;
            case DrawingCrown.CROWN_SILVER: setScale(crownK_silver); break;
            case DrawingCrown.CROWN_BRONZE: setScale(crownK_bronze); break;
        }
    }
}
