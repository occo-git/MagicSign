package com.softigress.magicsigns.UI._Main.Dialogs.PotionVideos.Item;

import android.graphics.Canvas;
import android.graphics.Typeface;
import androidx.annotation.Keep;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Controls.Achievements.AchievementType;
import com.softigress.magicsigns.UI._base.Controls._base.Buttons.BtnImageLabel;
import com.softigress.magicsigns.UI._base.Controls._base.Buttons.IClickListener;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns.UI._base.Effects.Glares.Glares;
import com.softigress.magicsigns.UI._base.Groups.Dialogs.IDialogItemControlTouchable;
import com.softigress.magicsigns._Base._Drawing.DrawingGroup;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._Base._Drawing._interfaces.ITouchable;
import com.softigress.magicsigns._system.Settings.Infos.SignInfos;
import com.softigress.magicsigns._system.Utils.AnimUtil;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.TaskUtils;
import com.softigress.magicsigns._system.Utils.TextUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class GrpPotionVideoItem extends DrawingGroup implements IDialogItemControlTouchable {

    private float cx, cy;
    private final float fw, fh;

    private final AchievementType achievementType;

    private final DrawingBase back;
    private final DrawingBase potion;
    private final DrawingText txtVideoCount;
    private final DrawingBase video;
    private final BtnImageLabel btnViewVideo;
    private final Glares glares;
    private final DrawingText txtReward;
    private final DrawingBase rewardBack;
    private final DrawingText txtAvailable;

    private Integer videoCount;
    private boolean isPotionAvailable = false;

    private float potionKx = .25f;
    @Keep
    public void setPotionKx(float kx) { this.potionKx = kx; }

    private float potionKs = .5f;
    @Keep
    public void setPotionKs(float ks) { this.potionKs = ks; }

    public GrpPotionVideoItem(final AchievementType achievementType, float cx, float cy, float fw, float fh) {
        this.achievementType = achievementType;

        this.cx = cx;
        this.cy = cy;
        this.fw = fw;
        this.fh = fh;

        float fx = cx - fw * .5f;
        float fy = cy - fh * .5f;

        back = new DrawingBase(.75f * fw, R.string.bmp_spot_white);//.bmp_halo_white_out);
        back.setPoint(fx + fw * .5f, fy + fh * .38f);
        back.setAngel(180);
        back.setAlpha(255 / 8);
        addDrawing(back);

        potion = new DrawingBase(potionKs * fh, SignInfos.getBitmapIdByAchievementType(achievementType));
        potion.setPoint(fx + fw * potionKx, fy + fh * .32f);
        addDrawing(potion);

        txtVideoCount = new DrawingText(DrawingHAlign.CENTER, TextUtils.dialog_text_xxsmall);
        txtVideoCount.setPoint(fx + fw * .75f, fy + fh * .25f);
        txtVideoCount.setText("0");
        addDrawing(txtVideoCount);

        rewardBack = new DrawingBase(.25f * fh, R.string.bmp_halo_white_out);
        rewardBack.setPoint(fx + fw * .75f, fy + fh * .25f);
        addDrawing(rewardBack);

        video = new DrawingBase(.25f * fh, R.string.bmp_video);
        video.setPoint(fx + fw * .75f, fy + fh * .25f);
        video.setAngel(-15);
        addDrawing(video);

        btnViewVideo = new BtnImageLabel(fx + fw * .5f, fy + fh * .68f, fw, fh * .25f);
        btnViewVideo.name = "btn_potion_video_" + achievementType;
        btnViewVideo.setBitmap(R.string.bmp_aim_ok);
        btnViewVideo.setLabelText(Utils.getRes(R.string.dlg_Potion_Video_View), Typeface.DEFAULT, TextUtils.lab_dialog_video_label);
        btnViewVideo.setStrokeWidth(PaintUtils.strokeWidth2);
        btnViewVideo.setListener(new IClickListener() {
            @Override
            public void handleOnClick(ITouchable e) {
                if (listener != null)
                    listener.onViewVideoClick(achievementType);
                // test onVideoReward(1);
            }
        });
        addDrawingTouchable(btnViewVideo);

        glares = new Glares(fx + fw * potionKx, fy + fh * .25f, fw * .5f, fh * .5f, fw / 6f, 1);
        addDrawing(glares);

        txtReward = new DrawingText(DrawingHAlign.CENTER, TextUtils.dialog_text_xxsmall);
        txtReward.setPoint(fx + fw, fy + fh * .2f);
        addDrawing(txtReward);

        txtAvailable = new DrawingText(DrawingHAlign.CENTER, TextUtils.dialog_text_xxsmall);
        txtAvailable.setPoint(fx + fw * .5f, fy + fh * .62f);
        txtAvailable.setText(R.string.dlg_Potion_Available);
        addDrawing(txtAvailable);

        setVideoCount(SignInfos.getVideoCountByAchievementType(achievementType)); // set the default video count
    }

    private IGrpPotionVideoItemListener listener;
    public void setListener(IGrpPotionVideoItemListener l) { this.listener = l; }

    private boolean isLoaded = false;
    public void setVideoCount(int count) {
        if (videoCount != null && videoCount > 0 && count <= 0) {
            back.setDefaultBitmap(R.string.bmp_spot_white);
            back.refreshCurrentStatus();
            new AnimUtil()
                    .add(this, "potionKx", .25f, .5f, .5f)
                    .add(this, "potionKs", .5f, .8f, .75f)
                    .startAD(1000);
        } else if (count > 0) {
            back.setDefaultBitmap(R.string.bmp_halo_white_out);
            back.refreshCurrentStatus();
            potionKx = .25f;
            potionKs = .5f;
        }

        this.videoCount = count;
        this.isLoaded = true;
        isPotionAvailable = (videoCount <= 0);
        if (txtVideoCount != null)
            txtVideoCount.setText(isPotionAvailable ? "" : videoCount.toString());
    }

    public void onVideoReward(Integer reward) {
        if (txtReward != null) {
            float ffx = cx + fw * .5f;
            float ffy = cy - fh * .3f;
            rewardBack.setAlpha(0);
            rewardBack.setPoint(cx + fw * .25f, cy - fh * .25f);
            rewardBack.show();
            txtReward.setAlpha(0);
            txtReward.setPoint(ffx, ffy);
            txtReward.setText("-" + reward.toString());
            txtReward.show();

            new AnimUtil()
                    .add(rewardBack, "alpha", 0, 64, 128, 0)
                    .add(rewardBack, "fd", 0, fh)
                    .add(txtReward, "alpha", 0, 128, 255, 0)
                    .add(txtReward, "fy", ffy, ffy - fh * .125f)
                    .startAD(2000);
            Utils.playSound(R.raw.bonus08, 100);

            TaskUtils.postDelayed(2100, new Runnable() {
                @Override public void run() {
                    rewardBack.hide();
                    txtReward.hide();
                }
            });
        }
    }

    @Override
    public void setAlpha(int a) {
        if (back != null)
            back.setAlpha(a / 8);
        if (potion != null)
            potion.setAlpha(a);
        if (txtVideoCount != null)
            txtVideoCount.setAlpha(a);
        if (video != null)
            video.setAlpha(a);
        if (btnViewVideo != null)
            btnViewVideo.setAlpha(a);
        if (txtAvailable != null)
            txtAvailable.setAlpha(a);
    }

    @Override
    public void setPoint(float cx, float cy) {
        this.cx = cx;
        this.cy = cy;
        calc();
    }

    @Override
    public void show() {
        super.show();
        if (glares != null)
            glares.show();
    }

    @Override
    public long hide() {
        if (glares != null)
            glares.hide();
        return super.hide();
    }

    //region draw
    @Override
    public void calc() {
        super.calc();

        float fx = cx - fw * .5f;
        float fy = cy - fh * .5f;
        if (back != null)
            back.setPoint(fx + fw * .5f, fy + fh * .38f);
        if (potion != null) {
            potion.setPoint(fx + fw * potionKx, fy + fh * .25f);
            potion.setFd(fh * potionKs);
        }
        if (txtVideoCount != null)
            txtVideoCount.setPoint(fx + fw * .75f, fy + fh * .25f);
        if (video != null)
            video.setPoint(fx + fw * .75f, fy + fh * .25f);
        if (btnViewVideo != null)
            btnViewVideo.setPoint(fx + fw * .5f, fy + fh * .68f);
        if (glares != null)
            glares.setBounds(fx + fw * potionKx, fy + fh * .25f, fw * .5f, fh * .5f);
        if (txtAvailable != null)
            txtAvailable.setPoint(fx + fw * .5f, fy + fh * .62f);
    }

    @Override
    protected void drawItems(Canvas c) {
        try {
            if (back != null)
                back.drawFrame(c);
            if (potion != null)
                potion.drawFrame(c);
            if (rewardBack != null)
                rewardBack.drawFrame(c);

            if (!isPotionAvailable) {
                if (txtVideoCount != null)
                    txtVideoCount.drawFrame(c);
                if (video != null)
                    video.drawFrame(c);
                if (btnViewVideo != null)
                    btnViewVideo.drawFrame(c);
            }
            //else if (txtAvailable != null)
            //    txtAvailable.drawFrame(c);

            if (glares != null)
                glares.drawFrame(c);
            if (txtReward != null)
                txtReward.drawFrame(c);

        } catch (Throwable t) {
            Utils.CrashReport("GrpPotionVideoItem.drawItems", t);
            throw t;
        }
    }
    //endregion
}
