package com.softigress.magicsigns.Activities._base;

import android.os.SystemClock;

public class PauseInfo {

    public boolean isPaused = false;
    private long pausedTicks = 0;

    public PauseInfo() { }

    public void pause() {
        pausedTicks = SystemClock.elapsedRealtime();
        isPaused = true;
    }

    public long resume() {
        isPaused = false;
        return SystemClock.elapsedRealtime() - pausedTicks;
    }
}
