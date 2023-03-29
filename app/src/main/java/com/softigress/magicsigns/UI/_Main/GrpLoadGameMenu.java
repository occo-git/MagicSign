package com.softigress.magicsigns.UI._Main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;

import com.softigress.magicsigns.Activities._base.ActivityGroupBase;
import com.softigress.magicsigns.BuildConfig;
import com.softigress.magicsigns.UI._base.Controls._base.Progress.CtrlProgressBar;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._system.Utils.TextUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class GrpLoadGameMenu extends ActivityGroupBase {

    private static final float fxTitle =  .50f,  fyTitle =     .32f;
    private static final float fxBar =     .50f, fyBar =       .62f;
    private static final float fwBar =     .38f, fhBar =       .025f;
    private static final float fxVersion = .90f, fyVersion =   .78f;
    private static final float fxCompany = .90f, fyCompany =   .80f;

    private final CtrlProgressBar ctrlProgressBar;

    public GrpLoadGameMenu() {
        super();

        DrawingText txtTitle = new DrawingText(DrawingHAlign.CENTER, TextUtils.load_game_title);
        txtTitle.setVerticalAlign(DrawingVAlign.CENTER);
        txtTitle.setText("magic/signs"); // при загрузке ресурсы еще не доступны
        txtTitle.setTextBack(8f, 64, 255, 255, 255);
        txtTitle.setMultiLineInterval(.9f);
        txtTitle.setPoint(fxTitle, fyTitle);
        txtTitle.setAlpha(255);
        addDrawing(txtTitle);

        ctrlProgressBar = new CtrlProgressBar(fxBar, fyBar, fwBar, fhBar);
        ctrlProgressBar.setRGB(0, 255, 0);
        ctrlProgressBar.show();
        addDrawing(ctrlProgressBar);

        //DrawingText txtStatus = new DrawingText(DrawingHAlign.CENTER, TextUtils.default_font_size);
        //txtStatus.setVerticalAlign(DrawingVAlign.TOP);
        //txtStatus.setPoint(fxBar, fyBar + 1.5f * fhBar);
        //addDrawing(txtStatus);

        DrawingText txtVersion = new DrawingText(DrawingHAlign.RIGHT, Typeface.DEFAULT, TextUtils.load_version);
        txtVersion.setVerticalAlign(DrawingVAlign.BOTTOM);
        txtVersion.setText(BuildConfig.VERSION_NAME);
        txtVersion.setPoint(fxVersion, fyVersion);
        addDrawing(txtVersion);

        DrawingText txtCompanyTitle = new DrawingText(DrawingHAlign.RIGHT, Typeface.DEFAULT, TextUtils.load_company_title);
        txtCompanyTitle.setVerticalAlign(DrawingVAlign.BOTTOM);
        txtCompanyTitle.setText("© 2021 softigress"); // из ресурсов не берем, они не инициализированны
        txtCompanyTitle.setPoint(fxCompany, fyCompany);
        addDrawing(txtCompanyTitle);
    }

    public void setProgress(float p, String text) {
        try {
            if (p < 0f) p = 0f;
            if (p > 1f) p = 1f;
            if (ctrlProgressBar != null) {
                ctrlProgressBar.setPercent(p);
                //int a = (int)(2.55f * ctrlProgressBar.getCurrentAnimPercent());
                //if (txtTitle != null)
                //    txtTitle.setAlpha(a);
            }
        } catch (Throwable t) {
            Utils.CrashReport("GrpLoadGameMenu.setProgress [" + p + "]", t);
        }
    }

    @Override
    public void drawFrame(Canvas c) {
        try {
            c.drawColor(Color.BLACK);
            super.drawFrame(c);
            //if (txtStatus != null && ctrlProgressBar != null)
            //    txtStatus.setText(ctrlProgressBar.getCurrentAnimPercent() + " %");
        } catch (Throwable t) {
            Utils.CrashReport("GrpLoadGameMenu.drawFrame", t);
        }
    }
}