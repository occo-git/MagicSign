package com.softigress.magicsigns.UI._Main.Dialogs.GameRate;

import com.softigress.magicsigns.Activities.MainActivity.MainActivityUtils;
import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Groups.Dialogs.GrpDialog;
import com.softigress.magicsigns._Base._Drawing._interfaces.IMoveListener;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchable;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchableListener;
import com.softigress.magicsigns._system.Utils.Utils;

public class GrpGameRateDialog extends GrpDialog {

    private static final float fyStar = .32f, fdStar = .075f;
    private final GameRateStar star1;
    private final GameRateStar star2;
    private final GameRateStar star3;
    private final GameRateStar star4;
    private final GameRateStar star5;
    private int stars = 0;

    public GrpGameRateDialog() {
        super(.8f, .62f, R.string.bmp_btn_ok, .5f, .85f);
        name = "dlg_rate_game";

        float fx0 = .16f;
        float btnStarStep = .68f / 4f;

        star1 = addStar(1, fx0, fyStar);
        star2 = addStar(2, fx0 + btnStarStep, fyStar);
        star3 = addStar(3, fx0 + 2 * btnStarStep, fyStar);
        star4 = addStar(4, fx0 + 3 * btnStarStep, fyStar);
        star5 = addStar(5, fx0 + 4 * btnStarStep, fyStar);

        setDlgMessageText(R.string.dlg_Rate, .5f, .15f);
        setDlgIdeaText(R.string.dlg_Rate_IdeaText, .5f, .55f);

        addDlgCloseButton();
    }

    private GameRateStar addStar(final int id, float dfx, float dfy) {
        GameRateStar star = new GameRateStar(fdStar);
        star.setListener(new ITouchableListener() {
            @Override public void handelOnTouch(ITouchable item) { onStar(id); }
            @Override public void handelOnTouchUp(ITouchable item) {  }
        });
        star.setListener(new IMoveListener() {
            @Override public void handelOnMoveIn(ITouchable item) { onStar(id); }
            @Override public void handelOnMoveOut(ITouchable item) { }
        });
        addDlgControlTouchable(star, dfx, dfy);
        return star;
    }

    @Override
    public void show() {
        super.show();
        stars = 0;
    }

    private void onStar(int id) {
        if (stars != id) {
            stars = id;
            star1.setStarOff(id < 1);
            star2.setStarOff(id < 2);
            star3.setStarOff(id < 3);
            star4.setStarOff(id < 4);
            star5.setStarOff(id < 5);
            Utils.playSound(R.raw.energy_catch02);
        }
    }

    public int getStars() { return stars; }
}
