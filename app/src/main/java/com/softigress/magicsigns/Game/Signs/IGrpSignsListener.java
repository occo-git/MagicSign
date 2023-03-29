package com.softigress.magicsigns.Game.Signs;

public interface IGrpSignsListener {
    void handleOnDnaTouch();
    void handleOnDnaBombTouch();
    void handleOnMultiplierTouch();
    void handleOnWin();
    void handleOnGameOver();
}
