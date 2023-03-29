package com.softigress.magicsigns.UI._Main.Dialogs;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Groups.Dialogs.GrpDialog;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._system.Utils.AnimUtil;

public class GrpNoConnectionDialog extends GrpDialog {

    private static final float dxNoConnectionDrop =       .5f, dyNoConnectionDrop =       .38f, fdNoConnectionDrop = .166f;
    private static final float dxNoConnectionMessage =    .5f, dyNoConnectionMessage =    .475f;
    private static final float dxNoConnectionIdea =       .5f, dyNoConnectionIdea =       .66f;
    private final DrawingBase dropNoConnection;

    public GrpNoConnectionDialog() {
        super(.8f, .62f);//.8f, .3f);

        setDlgMessageText(R.string.dlg_NoConnection_Message, dxNoConnectionMessage, dyNoConnectionMessage);
        setDlgIdeaText(R.string.dlg_NoConnection_Idea, dxNoConnectionIdea, dyNoConnectionIdea);

        dropNoConnection = new DrawingBase(fdNoConnectionDrop);
        dropNoConnection.setAlign(DrawingHAlign.CENTER, DrawingVAlign.BOTTOM);
        dropNoConnection.setDefaultBitmap(R.string.bmp_drop_90_sleep);
        addDlgControl(dropNoConnection, dxNoConnectionDrop, dyNoConnectionDrop);

        addRepeater(7000, new Runnable() { @Override public void run() { animateDrop(); } });
    }

    private void animateDrop() {
        new AnimUtil()
                .add(dropNoConnection, "widthScale",  1f,  .95f, 1.05f, 1f)
                .add(dropNoConnection, "heightScale", 1f, 1.05f,  .95f, 1f)
                .start(3000);

        /*int duration = 3000;
        ObjectAnimator sWS = ObjectAnimator.ofFloat(dropNoConnection, "widthScale",  1f,  .95f, 1.05f, 1f).setDuration(duration);
        ObjectAnimator sHS = ObjectAnimator.ofFloat(dropNoConnection, "heightScale", 1f, 1.05f,  .95f, 1f).setDuration(duration);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(sWS, sHS);
        set.setDuration(duration);
        //set.setInterpolator(new DecelerateInterpolator());
        set.start();*/
    }
}
