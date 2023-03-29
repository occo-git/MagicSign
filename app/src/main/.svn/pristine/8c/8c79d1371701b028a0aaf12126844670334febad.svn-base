package com.softigress.magicsigns.Game.Signs.SSequence;

import android.graphics.Canvas;
import com.softigress.magicsigns.Activities._base.PauseInfo;
import com.softigress.magicsigns.Game.Cells.Membrane.Membrane;
import com.softigress.magicsigns.Game.Puncher.RaySign;
import com.softigress.magicsigns.Game.Signs._base.DrawingSignCell;
import com.softigress.magicsigns.Game.Signs._base.IDrawingSignListener;
import com.softigress.magicsigns.Game.Signs._base.SignPunchType;
import com.softigress.magicsigns.UI._base.Controls.Lightning.LightningCollection;
import com.softigress.magicsigns._Base.ArrayRecyclableSimple;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchable;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Settings.Infos.SignInfo;
import com.softigress.magicsigns._system.Settings.Infos.SignInfoGroup;
import com.softigress.magicsigns._system.Settings.Infos.SignInfos;
import com.softigress.magicsigns._system.Settings.Infos.SignStrength;
import com.softigress.magicsigns._system.Utils.Utils;

import java.util.ArrayList;

public class SStep implements IDrawing, ITouchable {

    private final Integer id;
    private final float speedK;
    private final long startDelay;
    private int signsCount;
    private final SignStrength strength;
    private final boolean isGroups;
    private final boolean isCollapsed;
    private final boolean isLightning;

    public boolean isStarted = false;
    public boolean isFinished = false;
    private final PauseInfo pauseInfo = new PauseInfo();

    private boolean isOnFinish = false;
    private int finishAlpha = 255;
    public void setFinishAlpha(int a) {
        isOnFinish = true;
        finishAlpha = a;
    }

    private ArrayRecyclableSimple<DrawingSignCell> signs = new ArrayRecyclableSimple<>(DrawingSignCell.class);
    private DrawingSignCell[] signItems;

    public SStep(int id,
                 float speedK,          // скорость
                 int signsCount,        // количество signs
                 SignStrength strength, // тип signs
                 boolean isGroups,      // группа (переключающийся Sign)
                 boolean isCollapsed,   // сжаты
                 boolean isLightning)   // молнии
    {
        this.id = id;
        this.speedK = speedK;
        this.startDelay = Utils.getRandom(CurrentSettings.signDurationFluctuation); // флуктуация задержки
        this.signsCount = signsCount;
        if (this.signsCount > CurrentSettings.signMaxCount)
            this.signsCount = CurrentSettings.signMaxCount;
        this.strength = strength;
        this.isGroups = isGroups;
        this.isCollapsed = isCollapsed;
        this.isLightning = isLightning;
    }

    private IDrawingSignListener signListener;
    public void setListener(IDrawingSignListener l) { signListener = l; }

    //region start / pause / resume
    public void start() {
        if (signsCount > 0) {
            randomIndexes = new ArrayList<>();
            int count = signsCount;//Utils.getRandom(signsCount) + 1;
            for (int i = 0; i < count; i++) {
                long duration = (long)(speedK * CurrentSettings.signMaxDuration) + Utils.getRandom(CurrentSettings.signDurationFluctuation); // + флуктуация времени перемещения
                DrawingSignCell s = null;
                if (isGroups) { // переключающийся Sign
                    SignInfoGroup infoGroup = SignInfos.getRandomInfoGroupByParams(strength);
                    if (infoGroup != null)
                        s = new DrawingSignCell(infoGroup, CurrentSettings.signFr, duration, null);
                } else { // НЕпереключающийся Sign
                    SignInfo info = SignInfos.getRandomInfoByParams(strength);
                    if (info != null) {
                        s = new DrawingSignCell(info, CurrentSettings.signFr, duration, null);
                        if (isCollapsed)
                            s.collapse();
                        if (isLightning)
                            s.lightning();
                        if (signListener != null && !isCollapsed) // для отображения SignHelper
                            signListener.onShowHelper(info);
                    }
                }
                if (s != null) {
                    s.setFx(getNextRandomFx());
                    s.setListener(signListener);
                    s.start(startDelay);
                    signs.add(s);
                }
            }
            signItems = signs.getItems();
        }
        isStarted = true;
    }
    public void pause() {
        pauseInfo.pause();
        if (signItems != null)
            for (DrawingSignCell s : signItems)
                if (s != null)
                    s.pause();
    }
    public void resume() {
        if (signItems != null)
            for (DrawingSignCell s : signItems)
                if (s != null)
                    s.resume();
        pauseInfo.resume();
    }
    //endregion

    private ArrayList<Integer> randomIndexes;
    private float getNextRandomFx() {
        int randomIndex = Utils.getRandom(CurrentSettings.signMaxCount);
        for (int i = 0; i < CurrentSettings.signMaxCount * 2; i++) // повторяем генерацию случайного индекса несколько раз, пока не найдем свободный
            if (randomIndexes.contains(randomIndex))
                randomIndex = Utils.getRandom(CurrentSettings.signMaxCount);
            else
                break;
        randomIndexes.add(randomIndex);
        return CurrentSettings.signOffset + CurrentSettings.signFr + randomIndex * 2f * CurrentSettings.signFr;
    }

    DrawingSignCell getHelpSignCell() {
        if (signItems != null && isStarted && !isFinished) {
            float fy = 0;
            DrawingSignCell sign = null;
            for (DrawingSignCell s : signItems)
                if (s != null && !s.isGrouped() && !s.isCollapsed() && s.isInProgress()) {
                    float signFy = s.getFy();
                    if (signFy >= fy) {
                        fy = signFy;
                        sign = s;
                    }
                }
            return sign;
        }
        return null;
    }

    public int getPunched(RaySign raySign) {
        int punched = 0;
        if (signItems != null && isStarted && !isFinished)
            for (DrawingSignCell s : signItems)
                if (s != null && s.isInProgress() && s.getPunched(raySign))
                    punched++;
        return punched;
    }

    public int explode() {
        int exploded = 0;
        if (signItems != null && isStarted && !isFinished)
            for (DrawingSignCell s : signItems)
                if (s != null && s.isInProgress())
                    exploded += s.explode() ? 1 : 0;
        return exploded;
    }

    public void checkCollision(Membrane membrane) {
        if (signItems != null && isStarted && !isFinished)
            for (DrawingSignCell s : signItems)
                if (s != null && s.isInProgress() && membrane.checkCollision(s))
                    s.miss();
    }

    public void checkLightningHit(LightningCollection lightnings) {
        if (signItems != null && isStarted && !isFinished)
            for (DrawingSignCell s : signItems)
                if (s != null && s.isInProgress() && lightnings.checkCollision(s))
                    s.punch(SignPunchType.Lightning);
    }

    //region Touch
    public boolean onTouch(int x, int y) {
        if (signItems != null && isStarted && !isFinished)
            for (DrawingSignCell s : signItems)
                if (s != null)
                    s.onTouch(x, y);
        return true;
    }

    public boolean onTouchUp(int x, int y) {
        if (signItems != null && isStarted && !isFinished)
            for (DrawingSignCell s : signItems)
                if (s != null)
                    s.onTouchUp(x, y);
        return false;
    }

    public void onMoveTo(int x, int y) {
        if (signItems != null && isStarted && !isFinished)
            for (DrawingSignCell s : signItems)
                if (s != null)
                    s.onMoveTo(x, y);
    }
    //endregion

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    @Override
    public void calc() { }

    @Override
    public void drawFrame(Canvas c) {
        if (signItems != null && isStarted && !isFinished) {
            if (pauseInfo.isPaused) {
                for (DrawingSignCell s : signItems) {
                    if (s != null) {
                        if (s.isInProgress())
                            if (isOnFinish)
                                s.setAlpha(finishAlpha);
                        s.drawFrame(c);
                    }
                }
            } else {
                boolean finished = true;
                for (DrawingSignCell s : signItems) {
                    if (s != null) {
                        finished &= s.isFinished;
                        if (s.isInProgress())
                            if (isOnFinish)
                                s.setAlpha(finishAlpha);
                        s.drawFrame(c);
                    }
                }
                isFinished = finished;
            }
        }
    }
    //endregion

    @Override
    public void recycle() {
        if (signs != null)
            signs.recycle();
        signs = null;
    }
}
