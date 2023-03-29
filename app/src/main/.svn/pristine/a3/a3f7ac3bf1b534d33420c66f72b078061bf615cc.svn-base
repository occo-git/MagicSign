package com.softigress.magicsigns.UI._base.Controls.Bonuses;

import android.graphics.Canvas;
import com.softigress.magicsigns.R;
//import com.softigress.magicsigns._Base._Drawing.DrawingFrameRate;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.FireBase.Analytics.AnalyticsManager;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.Utils;

public class BonusCollection implements IDrawing {

    private static final int bonusCount = 7;
    private DrawingBonus[] bonuses;
    private int addIndex = 0;

    public boolean isEnabled = false;

    //private DrawingFrameRate frameRate;

    public BonusCollection() {
        bonuses = new DrawingBonus[bonusCount];
        //frameRate = new DrawingFrameRate("bonus", .04f, .2f);
    }

    public int addBonus(BonusType type, int count, float fx, float fy) {
        int bonus = 0;
        if (isEnabled) {
            String result = null;
            String title = null;
            switch (type) {
                case COMBO:
                    bonus = count * CurrentSettings.bonusCombo1;
                    result = String.format("combo %1$s", count);
                    title = String.format("%1$s *%2$d", Utils.getRes(R.string.bonus_combo_title), count);
                    break;
                case SEQUENCE:
                    bonus = count * CurrentSettings.bonusSequence1;
                    result = String.format("sequence %1$s", count);
                    title = String.format("%1$s *%2$d", Utils.getRes(R.string.bonus_sequence_title), count);
                    break;
                case STRIKE:
                    bonus = count * CurrentSettings.bonusStrike1;
                    result = String.format("strike %1$s", count);
                    title = String.format("%1$s *%2$d", Utils.getRes(R.string.bonus_strike_title), count);
                    break;
                case GUESS:
                    bonus = count;
                    result = "guess";
                    title = Utils.getRes(R.string.bonus_guess_title);
                    break;
                case LIGHTNING:
                    bonus = count;
                    result = "lightning";
                    title = Utils.getRes(R.string.bonus_lightning_title);
                    break;
            }
            if (result != null)
                Utils.LogEvent(AnalyticsManager.MS_EVENT_BONUS, result); // статистика получения бонуса

            if (bonus > 0 && title != null) {
                DrawingBonus b = new DrawingBonus(type, bonus, title);
                addIndex++;
                if (addIndex >= bonusCount)
                    addIndex = 0;
                bonuses[addIndex] = b;
                b.startAnim(fx, fy);
            }
        }
        return bonus;
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

        for (DrawingBonus b: bonuses)
            if (b != null)
                b.drawFrame(c);

        //if (frameRate != null)
        //    frameRate.drawFrame(c);
    }
    //endregion

    @Override
    public void recycle() {
        Utils.recycleArray(bonuses);
        bonuses = null;
        //if (frameRate != null)
        //    frameRate.recycle();
    }
}
