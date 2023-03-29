package com.softigress.magicsigns._system.Tasks.Repeaters;


import com.softigress.magicsigns._Base.IRecycle;

public class RunnableRepeater implements IRecycle {

    private final long repeatDuration;
    private final Runnable runnable;

    public RunnableRepeater(long repeatDuration, Runnable runnable) {
        this.repeatDuration = repeatDuration;
        this.runnable = runnable;
    }

    private long startTicks = 0;
    public void nextStep(long ticks) {
        if (startTicks == 0)
            startTicks = ticks;
        long delta = ticks - startTicks;
        if (delta > repeatDuration) {
            startTicks = 0;
            if (runnable != null)
                runnable.run();
        }
    }

    public void recycle() { }
}
