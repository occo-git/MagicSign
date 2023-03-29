package com.softigress.magicsigns.UI._base.Controls.Achievements;

import android.graphics.Canvas;
//import com.softigress.magicsigns._Base._Drawing.DrawingFrameRate;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.FireBase.Analytics.AnalyticsManager;
import com.softigress.magicsigns._system.Utils.Utils;

public class AchievementCollection implements IDrawing {

    private static final int achievementCount = 7;
    private DrawingAchievement[] achievements;
    private int addIndex = 0;

    public boolean isEnabled = false;

    //private DrawingFrameRate frameRate;

    public AchievementCollection() {
        achievements = new DrawingAchievement[achievementCount];
        //frameRate = new DrawingFrameRate("achievement", .04f, .2f);
    }

    public void addAchievement(AchievementType achievementType) {
        if (isEnabled) {
            Utils.LogEvent(AnalyticsManager.MS_EVENT_ACHIEVEMENT, achievementType.toString()); // статистика получения достижений

            DrawingAchievement ach = new DrawingAchievement(achievementType);
            addIndex++;
            if (addIndex >= achievementCount)
                addIndex = 0;
            achievements[addIndex] = ach;
            ach.startAnim();
        }
    }

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    @Override
    public void calc() { }

    @Override
    public void drawFrame(Canvas c) {
        //if (frameRate != null)
        //    frameRate.start();

        for (DrawingAchievement a: achievements)
            if (a != null)
                a.drawFrame(c);

        //if (frameRate != null)
        //    frameRate.drawFrame(c);
    }
    //endregion

    @Override
    public void recycle() {
        Utils.recycleArray(achievements);
        achievements = null;
        //if (frameRate != null)
        //    frameRate.recycle();
    }
}
