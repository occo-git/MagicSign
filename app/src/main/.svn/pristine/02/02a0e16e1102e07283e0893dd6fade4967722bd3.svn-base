package com.softigress.magicsigns.UI._base.Controls.Energy;

import android.graphics.Canvas;
import android.os.SystemClock;
//import com.softigress.magicsigns._Base._Drawing.DrawingFrameRate;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchable;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.Utils;

public class EnergyCollection implements IDrawing, ITouchable {

    private final int energyCount = 20; // одновременно обрабатываемых
    private DrawingEnergy[] energies;
    private int addIndex = 0;
    private int helpItemsCount = 3;

    public boolean isEnabled = false;

    //private DrawingFrameRate frameRate;

    public EnergyCollection() {
        energies = new DrawingEnergy[energyCount];
        //frameRate = new DrawingFrameRate("energy", .04f, .15f);
    }

    private IDrawingEnergyListener listener;
    public void setListener(IDrawingEnergyListener l) { this.listener = l; }

    public void startEnergy(float fx, float fy, EnergyType type) {
        DrawingEnergy e = new DrawingEnergy(CurrentSettings.signFr, type); // fd = signFr * .66f
        e.setListener(this.listener);
        e.start(fx, fy);
        if (helpItemsCount > 0) {
            helpItemsCount--;
            e.showHelp();
        }
        addIndex++;
        if (addIndex >= energyCount)
            addIndex = 0;
        energies[addIndex] = e;
    }

    //region finish / pause / resume
    public void finish() {
        for (DrawingEnergy e : energies)
            if (e != null && e.isInProgress())
                e.finish();
    }
    public void pause() {
        for (DrawingEnergy e : energies)
            if (e != null && e.isInProgress())
                e.pause();
    }
    public void resume() {
        for (DrawingEnergy e : energies)
            if (e != null && e.isInProgress())
                e.resume();
    }
    //endregion

    //region Touch
    public boolean onTouch(int x, int y) {
        if (isEnabled)
            for (DrawingEnergy e : energies)
                if (e != null && e.isInProgress())
                    e.onTouch(x, y);
        return true;
    }

    public boolean onTouchUp(int x, int y) {
        if (isEnabled)
            for (DrawingEnergy e : energies)
                if (e != null && e.isInProgress())
                    e.onTouchUp(x, y);
        return false;
    }

    public void onMoveTo(int x, int y) {
        if (isEnabled)
            for (DrawingEnergy e : energies)
                if (e != null && e.isInProgress())
                    e.onMoveTo(x, y);
    }
    //endregion

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    @Override
    public void calc() {
        /*synchronized (energies) {
            for (DrawingEnergy e : energies)
                if (e != null)
                    e.calc();
        }*/
    }

    @Override
    public void drawFrame(Canvas c) {
        //if (frameRate != null)
        //    frameRate.start();

        long ticks = SystemClock.elapsedRealtime();
            for (DrawingEnergy e : energies)
                if (e != null) {
                    e.nextFrame(ticks);
                    e.drawFrame(c);
                }
        //if (frameRate != null)
        //    frameRate.drawFrame(c);
    }
    //endregion

    @Override
    public void recycle() {
        Utils.recycleArray(energies);
        energies = null;
        //if (frameRate != null)
        //    frameRate.recycle();
    }
}
