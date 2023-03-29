package com.softigress.magicsigns.Story._base;

import com.softigress.magicsigns._Base.IRecycle;
import com.softigress.magicsigns._system.Utils.Utils;

public class StoryLineStep implements IRecycle {

    private long delay;
    private Runnable runnable;
    private boolean isActive;

    public StoryLineStep(long delay, Runnable runnable) {
        this.delay = delay;
        this.runnable = runnable;
    }

    public void check(long delta) {
        if (!isActive && delta > delay) {
            isActive = true;
            try {
                if (runnable != null)
                    runnable.run();
            } catch (Throwable t) {
                Utils.CrashReport("StoryLineStep run", t);
            }
        }
    }

    @Override
    public void recycle() {
        if (runnable != null)
            runnable = null;
    }
}
