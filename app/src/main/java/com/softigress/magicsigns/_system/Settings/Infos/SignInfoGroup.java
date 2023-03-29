package com.softigress.magicsigns._system.Settings.Infos;

import android.os.SystemClock;

import com.softigress.magicsigns._Base.ArrayRecyclable;
import com.softigress.magicsigns._Base.IRecycle;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.Utils;

public class SignInfoGroup implements IRecycle {

    private final int id;
    public final SignStrength strength;
    private static final int count = 4;
    private final ArrayRecyclable<SignInfo> sInfos;
    private int currentIndex;
    private long nextDuration = CurrentSettings.signRotationDuration;
    private long nextDurationOffset;

    public SignInfoGroup(int id, SignStrength strength, SignInfo info1, SignInfo info2, SignInfo info3, SignInfo info4) {
        this.id = id;
        this.strength = strength;

        sInfos = new ArrayRecyclable<>();
        sInfos.add(info1); // 0
        sInfos.add(info2); // 90
        sInfos.add(info3); // 180
        sInfos.add(info4); // 270
    }

    public SignInfoGroup getClone() {
        return new SignInfoGroup(id, strength, sInfos.get(0), sInfos.get(1), sInfos.get(2), sInfos.get(3));
    }

    public void setNextDuration(long duration) { nextDuration = duration; }

    private long startTicks = 0;
    public void start() {
        currentIndex = Utils.getRandom(count);
        nextDurationOffset = (long)(Utils.getRandom() * nextDuration);
        startTicks = SystemClock.elapsedRealtime() - nextDurationOffset;
    }

    public SignInfo getCurrent() {
        long ticks = SystemClock.elapsedRealtime();
        if (startTicks == 0)
            startTicks = ticks;
        long delta = ticks - startTicks;
        if (delta > nextDuration) {
            startTicks = 0;
            currentIndex++;
            if (currentIndex >= count)
                currentIndex = 0;
        }
        return sInfos.get(currentIndex);
    }

    public void recycle() {

    }
}
