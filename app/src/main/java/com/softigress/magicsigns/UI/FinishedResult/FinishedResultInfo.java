package com.softigress.magicsigns.UI.FinishedResult;

public class FinishedResultInfo {

    public boolean isWin = false;
    public int score = 0;
    public long ticks = 0;

    public FinishedResultInfo(boolean isWin, int score, long ticks) {
        this.isWin = isWin;
        this.score = score;
        this.ticks = ticks;
    }
}
