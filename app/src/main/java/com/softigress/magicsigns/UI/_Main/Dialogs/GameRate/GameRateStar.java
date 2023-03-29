package com.softigress.magicsigns.UI._Main.Dialogs.GameRate;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBaseTouchable;

class GameRateStar extends DrawingBaseTouchable {

    private final static int STAR_OFF = 0;
    private final static int STAR_ON = 1;

    public GameRateStar(float fd) {
        super(fd);
        setStatusBitmap(STAR_OFF, R.string.bmp_star_off);
        setStatusBitmap(STAR_ON, R.string.bmp_star_gold);
        setStatus(STAR_OFF);
    }

    public void setStarOff(boolean isOff) {
        setStatus(isOff ? GameRateStar.STAR_OFF : GameRateStar.STAR_ON);
    }
}
