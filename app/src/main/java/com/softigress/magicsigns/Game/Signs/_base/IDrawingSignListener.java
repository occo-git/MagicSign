package com.softigress.magicsigns.Game.Signs._base;

import com.softigress.magicsigns._system.Settings.Infos.SignInfo;

public interface IDrawingSignListener {
    void onShowHelper(SignInfo info);
    void onPunched(DrawingSignCell sign, SignPunchType punchType);
    void onMissed(SignInfo info);
    void onBreakCell(float fx, float fy);
}
