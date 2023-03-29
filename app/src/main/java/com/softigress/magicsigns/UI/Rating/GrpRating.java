package com.softigress.magicsigns.UI.Rating;

import android.animation.AnimatorSet;
import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI.Rating.Dialogs.GrpUserRatingDialog;
import com.softigress.magicsigns.UI.Rating.RatingRow.CtrlRatingRow;
import com.softigress.magicsigns.UI.Rating.RatingRow.ScoreInfo;
import com.softigress.magicsigns.UI.Rating.RatingRow.UserRatingRowInfo;
import com.softigress.magicsigns.UI._base.Controls.Drop.DropMotionType;
import com.softigress.magicsigns.UI._base.Controls.Drop.FunnyDrop;
import com.softigress.magicsigns.UI._base.Controls.Lightning.LightningCollection;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.MessageText;
import com.softigress.magicsigns.UI._base.Groups.Dialogs.GrpDialog;
import com.softigress.magicsigns.UI._base.Groups.Dialogs.IGrpDialogListener;
import com.softigress.magicsigns.UI._base.Groups.Dialogs.IGrpDialogResultListener;
import com.softigress.magicsigns.UI._base.Groups.GrpUserInfos;
import com.softigress.magicsigns._Base.ArrayRecyclable;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchable;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchableListener;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.AnimUtil;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.TaskUtils;
import com.softigress.magicsigns._system.Utils.TextUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class GrpRating extends GrpUserInfos {

    private static final float xCaption =      .50f, yCaption =         .075f;
    private static final float xComment =      .50f, yComment =         .41f;
    private static final float yFirstRow =     .16f;
    private static final float xDropWait =     .50f, yDropWait =        .62f, fdDropWait =      .133f;
    private static final float xDropProgress = .50f, yDropProgress =    .90f, fdDropProgress =  .133f;
    private static final int ctrlsCount  = CurrentSettings.rateRowsCount;

    private final DrawingText txtCaption;
    private final FunnyDrop dropWait;
    private final FunnyDrop dropProgress;
    private final MessageText txtSignInComment;
    private final LightningCollection lightnings;

    private final ArrayRecyclable<CtrlRatingRow> ctrlRatingRows;
    private final GrpUserRatingDialog dlgUserRating;

    public GrpRating() {
        super(true, true); // super(R.drawable.back_14, false // NoConnectionDialog OFF, true // OptionsDialog ON
        // отключаем выдачу сообщения при отсутствии связи

        if (btnLogIn.isVisible())
            btnLogIn.setLabelAlign(DrawingHAlign.CENTER);

        lightnings = new LightningCollection();
        addDrawing(lightnings);
        addRepeater(14000, new Runnable() {
            @Override
            public void run() {
                lightnings.addLightning(
                        CurrentSettings.signLightningDuration, CurrentSettings.signLightningFd,
                        PaintUtils.strokeWidth2, PaintUtils.strokeWidth * 3f,
                        Utils.getRandom(), Utils.getRandom(), - Math.PI / 4f); // Utils.getRandomMathSign() *
            }
        });

        //region caption
        txtCaption = new DrawingText(DrawingHAlign.CENTER, TextUtils.rating_caption);
        txtCaption.setPoint(xCaption, yCaption);
        txtCaption.setTextBack(6f, 32, 255, 255, 255);
        txtCaption.setText(R.string.rating_caption);
        addDrawing(txtCaption);
        //endregion

        //region rows
        float dy = (1f - .45f) / (ctrlsCount + 1);
        float fh = 1f * dy;
        float fy = yFirstRow;

        ctrlRatingRows = new ArrayRecyclable<>();
        // rows + 1 контролов
        // +1 один отдельрный контрол для отображения информации по текущему пользователю ctrlRatingRows.get(0), не вошедшему в число первых
        for (int r = 0; r <= ctrlsCount; r++) {
            CtrlRatingRow ctrl = null;
            if (r == 0) // если контрол информации текущего пользователя, не вошедшему в число первых
                ctrl = new CtrlRatingRow(fy + dy * ctrlsCount + dy / 2, fh);
            else
                ctrl = new CtrlRatingRow(fy + dy * (r - 1), fh);
            ctrlRatingRows.add(ctrl);
        }
        // для отрисовки добавляем контролы в обратном порядке
        for (int r = ctrlsCount; r >= 0; r--) {
            CtrlRatingRow ctrl = ctrlRatingRows.get(r);
            if (ctrl != null) {
                ctrl.setListener(new ITouchableListener() {
                    @Override
                    public void handelOnTouch(ITouchable item) { }
                    @Override
                    public void handelOnTouchUp(ITouchable item) {
                        CtrlRatingRow ctrl = (CtrlRatingRow)item;
                        if (ctrl != null) {
                            Utils.playSoundClick();
                            UserRatingRowInfo info = ctrl.getUserRatingRowInfo();
                            if (info != null) {
                                dlgUserRating.setInfo(info);
                                showDialog(dlgUserRating);
                            }
                        }
                    }
                });
                addDrawingTouchable(ctrl);
            }
        }
        //endregion

        //region comment
        txtSignInComment = new MessageText(DrawingHAlign.CENTER);
        txtSignInComment.setVerticalAlign(DrawingVAlign.BOTTOM);
        txtSignInComment.setFontSize(TextUtils.rating_comment);
        txtSignInComment.isPaintRect = true;
        txtSignInComment.isIdea = true;
        txtSignInComment.setTextBack(4f, 32, 0, 0, 0);
        txtSignInComment.setText(R.string.rating_comment);
        txtSignInComment.setPoint(xComment, yComment);
        txtSignInComment.hide();
        addDrawing(txtSignInComment);
        //endregion

        //region drop
        dropWait = new FunnyDrop(fdDropWait);
        dropWait.loadStatuses(new int[] {
                //FunnyDrop.STATUS_10_SIMPLE,
                //FunnyDrop.STATUS_11_SIMPLE_BLINK,
                FunnyDrop.STATUS_20_INTEREST,
                FunnyDrop.STATUS_21_INTEREST_BLINK,
                //FunnyDrop.STATUS_30_SURPRISE,
                //FunnyDrop.STATUS_40_JOKE,
                //FunnyDrop.STATUS_50_LAUGH,
                //FunnyDrop.STATUS_60_HAPPY,
                //FunnyDrop.STATUS_61_HAPPY_BLINK,
                //FunnyDrop.STATUS_70_PUNCHED,
                //FunnyDrop.STATUS_80_PRO,
                FunnyDrop.STATUS_90_SLEEP,
                FunnyDrop.STATUS_100_WAIT,
                FunnyDrop.STATUS_101_WAIT_BLINK,
        });
        dropWait.setMessageFontSize(TextUtils.rating_drop_message);
        dropWait.addMessageTexts(
                "progressSleep",
                FunnyDrop.MES_SLEEP,
                new int[] {
                    0, 0, R.string.message_drop_sleep_01,
                    0, 0, R.string.message_drop_sleep_02,
                    1, 0, R.string.message_drop_sleep_03,
                });
        dropWait.setMessagePoint(xDropWait, yDropWait - .8f * fdDropWait);
        dropWait.setListener(new ITouchableListener() {
                                 @Override
                                 public void handelOnTouch(ITouchable item) {
                                     if (dropWait.statusId != FunnyDrop.STATUS_90_SLEEP)
                                        showDialog(dlgLogin);
                                 }
                                 @Override
                                 public void handelOnTouchUp(ITouchable item) { }
                             });
        dropWait.setPoint(xDropWait, yDropWait);
        dropWait.isAllowMotion = true;
        dropWait.hide();
        addDrawingTouchable(dropWait);

        dropProgress = new FunnyDrop(fdDropProgress);
        dropProgress.loadStatuses(new int[] {
                //FunnyDrop.STATUS_10_SIMPLE,
                //FunnyDrop.STATUS_11_SIMPLE_BLINK,
                FunnyDrop.STATUS_20_INTEREST,
                FunnyDrop.STATUS_21_INTEREST_BLINK,
                //FunnyDrop.STATUS_30_SURPRISE,
                //FunnyDrop.STATUS_40_JOKE,
                //FunnyDrop.STATUS_50_LAUGH,
                FunnyDrop.STATUS_60_HAPPY,
                FunnyDrop.STATUS_61_HAPPY_BLINK,
                FunnyDrop.STATUS_70_PUNCHED,
                //FunnyDrop.STATUS_80_PRO,
                //FunnyDrop.STATUS_90_SLEEP,
                FunnyDrop.STATUS_100_WAIT,
                FunnyDrop.STATUS_101_WAIT_BLINK,
        });
        dropProgress.setListener(new ITouchableListener() {
            @Override
            public void handelOnTouch(ITouchable item) {
                if (dropProgress.statusId != FunnyDrop.STATUS_70_PUNCHED) {
                    dropProgress.setStatus(FunnyDrop.STATUS_70_PUNCHED);
                    Utils.playSound(R.raw.punch03);
                }
            }
            @Override
            public void handelOnTouchUp(ITouchable item) { }
        });
        dropProgress.setPoint(xDropProgress, 1f + 2f * fdDropProgress);
        dropProgress.isAllowMotion = true;
        addDrawingTouchable(dropProgress);

        //endregion

        //region rating dialog
        dlgUserRating = new GrpUserRatingDialog();
        dlgUserRating.setListener(new IGrpDialogListener() {
            @Override
            public void handleOnOk() { hideDialog(); }
            @Override
            public void handleOnCancel() { hideDialog(); }
        });
        dlgUserRating.setListener(new IGrpDialogResultListener() {
            @Override
            public void handleOnResult(int result) {
                if (result == GrpUserRatingDialog.DIALOG_RESULT_VIDEOS)
                    showDialog(dlgPotionVideos);
            }
        });
        addDialog(dlgUserRating);
        //endregion

        addPotionVideoDialog();
    }

    //@Override
    //protected boolean isStars() { return true; }
    //@Override
    //protected boolean isStarsMoving() { return true; }

    @Override
    protected int getMusicId() { return 3003; } // transcendence

    //region pause / resume
    @Override
    public void onPauseGroup(GrpDialog dlg) {
        if (dlg != null || !isCurrentDialog(dlgPotionVideos))
            showDialog(dlg);
    }
    //@Override
    //public void onResumeGroup() { }
    //endregion

    //region connection
    @Override
    public void onConnectionOn() {
        super.onConnectionOn();
        refreshControls();
    }
    @Override
    public void onConnectionOff() {
        super.onConnectionOff();
        hideRatingRows();
        //animateDropSleep();
    }
    //endregion

    //region show / hide
    @Override
    public void show() {
        super.show();
        hideRatingRows();
        //animateDropWait();
    }

    private void hideRatingRows() {
        for (int r = 0; r <= ctrlsCount; r++) {
            CtrlRatingRow ctrl = ctrlRatingRows.get(r);
            if (ctrl != null)
                ctrl.hide();
        }
    }
    //endregion

    //region show/hide sign in comment
    private void showSignInComment() {
        if (txtSignInComment != null)
            txtSignInComment.show();
    }
    private void hideSignInComment() {
        if (txtSignInComment != null)
            txtSignInComment.hide();
    }
    //endregion

    //region drop wait animation
    private void animateDropSleep() {
        if (dropWait != null) {
            if (dropWait.statusId != FunnyDrop.STATUS_90_SLEEP) {
                long sleepDuration = dropWait.setMotion(DropMotionType.SLEEP);
                hideDropWait(sleepDuration); // скрываем dropWait после анимации, если необходимо
            }
        }
    }
    private void animateDropWait() {
        if (dropWait != null) {
            if (dropWait.statusId != FunnyDrop.STATUS_100_WAIT && dropWait.statusId != FunnyDrop.STATUS_101_WAIT_BLINK) {
                long waitDuration = dropWait.setMotion(DropMotionType.WAIT);
                hideDropWait(waitDuration + 500); // скрываем dropWait после анимации, если необходимо
            }
        }
    }
    private void hideDropWait(long delay) {
        TaskUtils.postDelayed(delay, new Runnable() {
            @Override
            public void run() {
                if (topRowInfos != null)
                    hideDropWait();
            }
        });
    }
    private void hideDropWait() {
        if (dropWait != null)
            dropWait.hide();
    }
    //endregion

    //region drop received animation
    private boolean isOnAction = false;
    private AnimatorSet animSet;
    private void cancelAnim() {
        if (animSet != null && animSet.isStarted())
            animSet.cancel();
    }
    private void onAction(long duration) {
        isOnAction = true;
        cancelAnim();
        TaskUtils.postDelayed(duration, new Runnable() {
            @Override
            public void run() { isOnAction = false; }
        });
    }
    private void animateDropReceived() {
        if (!isOnAction) {
            cancelAnim();
            //dropProgress.setPoint(-.2f, yDropProgress);
            float ffy = 1f + 2f * fdDropProgress;
            dropProgress.setPoint(xDropProgress, ffy);
            //dropProgress.setStatus(FunnyDrop.STATUS_100_WAIT);
            dropProgress.setMotion(DropMotionType.HAPPY);
            long duration = 5000;
            onAction(duration);
            // старт анимации движения
            animSet = new AnimUtil()
                    .add(dropProgress, "fy", ffy, yDropProgress, yDropProgress, yDropProgress, ffy)
                    .add(dropProgress, "widthScale", 1f, .975f, 1.1f, .975f, 1f)
                    .add(dropProgress, "heightScale", .95f, 1.05f, .975f, 1.05f, .95f)
                    .start(duration);

            /*ObjectAnimator sY = ObjectAnimator.ofFloat(dropProgress, "fy", ffy, yDropProgress, yDropProgress, yDropProgress, ffy).setDuration(duration);
            ObjectAnimator sWS = ObjectAnimator.ofFloat(dropProgress, "widthScale", 1f, .975f, 1.1f, .975f, 1f).setDuration(duration);
            ObjectAnimator sHS = ObjectAnimator.ofFloat(dropProgress, "heightScale", .95f, 1.05f, .975f, 1.05f, .95f).setDuration(duration);
            animSet = new AnimatorSet();
            animSet.playTogether(sY, sWS, sHS);
            animSet.setDuration(duration);
            //animSet.setInterpolator(new AccelerateInterpolator());
            animSet.start();*/
        }
    }
    //endregion

    @Override
    protected void refreshControls() {
        super.refreshControls();

        String userId = Utils.authManager.getUserId();
        if (userId == null) {
            hideRatingRows();
            if (topRowInfos == null)
                animateDropWait();
            showSignInComment(); // отображаем комментарий о необходимости залогиниться
            // если открыт диалог рейтинга пользователя
            if (isCurrentDialog(dlgUserRating))
                hideDialog(); // закрываем диалог

        } else if (topRowInfos != null) {

            hideDropWait();
            hideSignInComment();

            //region открытый диалог рейтинга выбранного пользователя
            boolean isUserRatingDialogOn = isCurrentDialog(dlgUserRating);
            UserRatingRowInfo dialogUserRatingRowInfo = null;
            String dialogUserRatingInfoId = null;
            if (isUserRatingDialogOn)
                dialogUserRatingInfoId = dlgUserRating.getUserInfoId(); // получим id пользователя, чей диалог рейтинга открыт
            //endregion

            // информация по текущему пользователю, вошедшему в число первых
            UserRatingRowInfo topCurrentUserRatingRowInfo = null;
            //region установим индексы
            for (UserRatingRowInfo rowInfo : topRowInfos)
                if (rowInfo != null) {
                    // данные текущего пользователя
                    if (rowInfo.id.equals(userId))
                        topCurrentUserRatingRowInfo = rowInfo;
                    // информация по пользователю в открытом диалоге рейтинга
                    if (rowInfo.id.equals(dialogUserRatingInfoId))
                        dialogUserRatingRowInfo = rowInfo;
                }
            //endregion

            // если открыт диалог рейтинга определенного пользователя
            if (isUserRatingDialogOn && dialogUserRatingRowInfo != null)
                dlgUserRating.setInfo(dialogUserRatingRowInfo);

            //region заполняем данными строки таблицы
            int rowsCount = topRowInfos.size();
            // первые по количеству очков
            for (int r = 0; r < ctrlsCount; r++) {
                CtrlRatingRow ctrl = ctrlRatingRows.get(r + 1);
                if (ctrl != null) {
                    ctrl.reset(); // empty
                    if (r < rowsCount) {
                        UserRatingRowInfo info = topRowInfos.get(r);
                        ctrl.setUserRatingRowInfo(info, info.id.equals(userId)); // set info
                        ctrl.show();
                    }
                }
            }

            // информация по текущему пользователю, не вошедшему в число первых
            UserRatingRowInfo currentUserRatingRowInfo = null;
            // если текущий пользователе не найден в списке
            if (topCurrentUserRatingRowInfo == null && userRowInfo != null && scores != null) {
                // определим индекс пользователя в рейтинге
                for (ScoreInfo score : scores)
                    if (score.id.equals(userRowInfo.id)) {
                        userRowInfo.index = score.index;
                        break;
                    }
                if (userRowInfo.index > ctrlsCount)
                    currentUserRatingRowInfo = userRowInfo;
            }

            // нижняя строка рейтинга
            CtrlRatingRow ctrl = ctrlRatingRows.get(0);
            if (ctrl != null) {
                ctrl.reset();
                // если пользователь не в числе первых
                if (currentUserRatingRowInfo != null) {
                    ctrl.setUserRatingRowInfo(currentUserRatingRowInfo, true);
                    ctrl.show();
                } else
                    ctrl.hide();
                    animateDropReceived();
            }
            //endregion

            // анимация drop
            if (currentUserRatingRowInfo != null && currentUserRatingRowInfo.index > ctrlsCount)
                dropProgress.hide();//.setPoint(-.2f, yDropProgress);
            else
                animateDropReceived();
        }
    }

    /*private int dropActionDuration = 3000;
    private long startDropActionTicks = 0;
    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);

        long ticks = SystemClock.uptimeMillis();
        if (startDropActionTicks == 0)
            startDropActionTicks = ticks;
        long deltaBlink = ticks - startDropActionTicks;
        if (deltaBlink > dropActionDuration) {
            startDropActionTicks = 0;
            if (dropWait.statusId == FunnyDrop.STATUS_100_WAIT || dropWait.statusId == FunnyDrop.STATUS_101_WAIT_BLINK) {
                long duration = 2000;
                // переодическое движение в статусе wait
                ObjectAnimator sWS = ObjectAnimator.ofFloat(dropWait, "widthScale", 1f, .9f, 1f).setDuration(duration);
                ObjectAnimator sHS = ObjectAnimator.ofFloat(dropWait, "heightScale", 1f, 1.05f, 1f).setDuration(duration);
                //synchronized (animWaitSet) {
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(sWS, sHS);
                    set.setDuration(duration);
                    //set.setInterpolator(new AccelerateDecelerateInterpolator());
                    set.start();
                //}
            }
        }
    }*/

    @Override
    public void recycle() {
        super.recycle();

        if (lightnings != null)
            lightnings.recycle();
        if (ctrlRatingRows != null)
            ctrlRatingRows.recycle();

        //lightnings = null;
        //ctrlRatingRows = null;
    }
}
