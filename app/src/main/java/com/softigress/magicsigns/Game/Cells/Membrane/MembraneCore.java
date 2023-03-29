package com.softigress.magicsigns.Game.Cells.Membrane;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.os.SystemClock;

import com.softigress.magicsigns.Game.Cells._base.DrawingCell;

public class MembraneCore extends DrawingCell {

    private static final int activateDuration = 2000;
    private static final int disableDuration = 2000;

    boolean isActive = false;
    private final float coverFr;

    private final MembraneActivatorCollection activatorCollection;
    private final MembraneCharger charger;

    public MembraneCore(float fx, float fy, float fr, int podCount) {
        super(fr, podCount, fr / 150f, fr / 100f);

        coverFr = fr;

        setPoint(fx, fy);
        setAngelsDegrees(Membrane.degreesStart, Membrane.degreesEnd);
        setPodDuration(Membrane.podDuration);
        setWaves(Membrane.membraneWavesCount);
        isStarted = true;

        activatorCollection = new MembraneActivatorCollection(fr, podCount);
        charger = new MembraneCharger();

        // disabled by default
        setFr(coverFr * .8f);
        setColor(0, 255, 255, 255);
        setAlpha(0);
    }

    public MembraneCore() {
        this(Membrane.cx, Membrane.cy, Membrane.membraneFr, Membrane.podCount);
    }

    //region activate / deactivate / disable
    public int activate() {
        if (!isActive) {
            setFr(coverFr);
            chargerReset();
            isActive = true;
        }
        if (activatorCollection != null)
            activatorCollection.activate(activateDuration);
        return activateDuration;
    }

    public int deactivate() {
        if (activatorCollection != null)
            activatorCollection.deactivate(disableDuration);
        return disableDuration;
    }

    public void disable() {
        chargerReset();
        isActive = false;
    }
    //endregion

    //region charge
    public void setChargeFullAmount(int amount) {
        if (charger != null)
            charger.setChargeFullAmount(amount);
    }
    public float charge(int amount) {
        if (charger != null)
            return charger.charge(amount);
        return 0;
    }
    public boolean isCharged() {
        if (charger != null)
            return charger.isCharged();
        return false;
    }
    public void chargerReset() {
        if (charger != null)
            charger.reset();
    }
    private ObjectAnimator frAnim  = null;
    public void podFrAnim(float podFr, int delay) {
        if (frAnim != null && frAnim.isStarted())
            frAnim.cancel();
        frAnim = null;
        float prevPodFr = getPodFr();
        frAnim = ObjectAnimator.ofFloat(this, "PodFr", prevPodFr, podFr).setDuration(2000);
        if (delay > 0)
            frAnim.setStartDelay(delay);
        frAnim.start();
    }
    //endregion

    //region alpha/color
    @Override
    public void setAlpha(int a) {
        super.setAlpha(a);
        this.paint1.setAlpha(a / 4);
    }
    @Override
    public void setColor(int a, int r, int g, int b) {
        super.setColor(a, r, g, b);
        this.paint1.setARGB(a / 4, r, g, b);
    }
    //endregion

    private long startActiveTicks = 0;
    private long startDisableTicks = 0;
    private void nextFrame() {
        if (isActive) {
            if (alpha < 255) {
                long ticks = SystemClock.elapsedRealtime();
                if (startActiveTicks == 0)
                    startActiveTicks = ticks;
                long delta = ticks - startActiveTicks;
                int a = (int)(255f * delta / activateDuration);
                if (a >= 255) {
                    a = 255;
                    startActiveTicks = 0;
                }
                setAlpha(a);
            }
        } else { // !isActive
            if (alpha != 0) {
                long ticks = SystemClock.elapsedRealtime();
                if (startDisableTicks == 0)
                    startDisableTicks = ticks;
                long delta = ticks - startDisableTicks;
                int a = 255 - (int)(255f * delta / disableDuration);
                if (a <= 0) {
                    a = 0;
                    startDisableTicks = 0;
                }
                setAlpha(a);
            }
        }
    }

    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);
        if (!isHelpItem) {
            nextFrame();
            // активация / деактивация
            if (activatorCollection != null)
                for (MembraneActivator a : activatorCollection.activators)
                    if (a != null && a.isOnAction)
                        a.drawFrame(c);
        }
    }

    @Override
    public void recycle() {
        super.recycle();

        if (activatorCollection != null)
            activatorCollection.recycle();
    }
}
