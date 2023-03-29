package com.softigress.magicsigns.UI._base.Controls._base.Buttons;

import android.animation.Animator;
import android.graphics.Bitmap;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBaseTouchable;
import com.softigress.magicsigns._system.FireBase.Analytics.AnalyticsManager;
import com.softigress.magicsigns._system.Utils.TaskUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class DrawingButton extends DrawingBaseTouchable {

    static final int clickDuration = 400;
    private boolean isEnabled = true;
    private int enabledBitmapId = 0;
    private int disabledBitmapId = 0;

    private static final int BUTTON_STATUS_DEFAULT = 0;
    private static final int BUTTON_STATUS_PRESSED = 1;

    public String name;
    public int clickSoundId = R.raw.ui_item_click15;
    private IClickListener listener;

    public DrawingButton(float fx, float fy, float fw, float fh) {
        super(fx, fy, fw, fh);
    }

    public DrawingButton(float fx, float fy, float fd) {
        super(fx, fy, fd);
    }

    public DrawingButton(float fx, float fy, float fd, Bitmap bitmap) {
        super(fx, fy, fd);
        setDefaultBitmap(bitmap);
    }

    public DrawingButton(float fx, float fy, float fd, int defaultId) {
        this(fx, fy, fd, defaultId, defaultId, defaultId);
    }

    public DrawingButton(float fx, float fy, float fd, int defaultId, int pressedId, int disabledId) {
        super(fx, fy, fd);
        setBitmapIds(defaultId, pressedId, disabledId);
    }

    public DrawingButton(float fx, float fy, float fw, float fh, int defaultId, int pressedId, int disabledId) {
        super(fx, fy, fw, fh);
        setBitmapIds(defaultId, pressedId, disabledId);
    }

    private void setBitmapIds(int defaultId, int pressedId, int disabledId) {
        enabledBitmapId = defaultId;
        disabledBitmapId = disabledId;

        setStatusBitmap(BUTTON_STATUS_DEFAULT, defaultId);
        setStatusBitmap(BUTTON_STATUS_PRESSED, pressedId);
        setStatus(BUTTON_STATUS_DEFAULT);
    }

    public void setListener(IClickListener l) {
        this.listener = l;
    }

    public void enable() {
        isEnabled = true;
        setDefaultBitmap(enabledBitmapId);
    }
    public void disable() {
        isEnabled = false;
        setDefaultBitmap(disabledBitmapId);
    }

    //region touch
    @Override
    protected void onTouch() {
        super.onTouch();
        if (isEnabled)
            setStatus(BUTTON_STATUS_PRESSED);
    }

    @Override
    protected void onTouchUp() {
        super.onTouchUp();
        if (isEnabled) {
            setStatus(BUTTON_STATUS_DEFAULT);
            onClick();
        }
    }

    @Override
    protected void onMoveOut() {
        super.onMoveOut();
        if (isEnabled)
            setStatus(BUTTON_STATUS_DEFAULT);
    }
    //endregion

    private Animator clickAnimator;
    void applyClickAnimator(Animator anim) {
        clickAnimator = anim;
        TaskUtils.onAnimStart(anim, new Runnable() { @Override public void run() {
            handleOnClick();
        } });
    }

    private boolean isOnClick = false;
    private void onClick() {
        if (!isOnClick)
            onButtonClick();
    }

    protected void onButtonClick() {
        isOnClick = true;
        if (clickSoundId != 0)
            Utils.playSound(clickSoundId);
        if (clickAnimator != null) {
            clickAnimator.cancel();
            clickAnimator.start();
        } else
            handleOnClick();
    }

    private void handleOnClick() {
        Utils.LogEvent(AnalyticsManager.MS_EVENT_BTN_CLICK, name);
        if (listener != null)
            listener.handleOnClick(this);
        isOnClick = false;
    }

    @Override
    public void recycle() {
        super.recycle();
        if (clickAnimator != null) {
            clickAnimator.removeAllListeners();
            clickAnimator = null;
        }
    }
}