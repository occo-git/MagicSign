package com.softigress.magicsigns.UI._Main.Dialogs.Help;

import android.graphics.Canvas;

import com.softigress.magicsigns.Game.Cells.Membrane.MembraneCore;
import com.softigress.magicsigns.Game.Dna.ProgressDna.ProgressDna;
import com.softigress.magicsigns.UI._base.Controls.Energy.DrawingEnergy;
import com.softigress.magicsigns.UI._base.Controls.Energy.EnergyType;
import com.softigress.magicsigns.Game.Signs._base.DrawingSignCell;
import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI.Lab.LabIndex;
import com.softigress.magicsigns.UI._base.Controls._base.Counters.CtrlMultiplier;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Settings.Infos.SignInfos;
import com.softigress.magicsigns._system.Settings.Infos.SignStrength;
import com.softigress.magicsigns._system.Utils.TextUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class HelpItem extends DrawingBase {

    private IDrawing currentItem;
    private DrawingSignCell cell;
    private MembraneCore core;
    private CtrlMultiplier ctrlMultiplier;
    private DrawingEnergy energyElement;
    private ProgressDna dna;
    private DrawingEnergy dnaElement;
    private DrawingEnergy dnaBombElement;
    private DrawingText txtSecret;
    private LabIndex labIndex;

    public HelpItem(float fx, float fy, float fd) {
        super(fx, fy, fd);

        try {
            // TYPE_STARS +
            // TYPE_VIRAL_CELLS
            cell = new DrawingSignCell(SignInfos.getRandomInfoByParams(SignStrength.SIMPLE), .05f, 3000);
            cell.isHelpItem = true;
            cell.isStarted = true;
            // TYPE_WAVES +
            // TYPE_MEMBRANE
            core = new MembraneCore(fx, fy + 5f * fd, 5f * fd, 30);
            core.activate();
            core.isHelpItem = true;
            // TYPE_PROTECTION_LEVEL
            ctrlMultiplier = new CtrlMultiplier();
            ctrlMultiplier.setMultiplier(2);
            ctrlMultiplier.setPercent(.75f);
            // TYPE_ENERGY
            energyElement = new DrawingEnergy(fd / 2f, EnergyType.SIMPLE);
            energyElement.isHelpItem = true;
            // TYPE_DNA
            dna = new ProgressDna(1.75f * fd); // isHorizontal
            dna.isHelpItem = true;
            dna.startWaves(1.5f);
            dna.reset();
            dna.setPodsOn(CurrentSettings.dnaPodsCount - 5);
            dna.setReadyPodsCount(5);
            dna.show();
            // TYPE_DNA_ELEMENT
            dnaElement = new DrawingEnergy(fd / 2f, EnergyType.DNA);
            dnaElement.isHelpItem = true;
            // TYPE_DNA_BOMB
            dnaBombElement = new DrawingEnergy(fd, EnergyType.EXPLODE);
            dnaBombElement.isHelpItem = true;
            dnaBombElement.setMaxAlpha(255);
            dnaBombElement.setAlpha(255);
            // TYPE_LAB
            txtSecret = new DrawingText(DrawingHAlign.CENTER, TextUtils.lab_help_secret);
            txtSecret.setTextBack(6f, 32, 255, 255, 255);
            txtSecret.setText("? + ? = ?");
            // TYPE_MARKER
            labIndex = new LabIndex(5, 0f, 0f);

            setType(HelpItemInfo.TYPE_NONE);

        } catch (Throwable t) {
            Utils.CrashReport("HelpItem", t);
        }
    }

    public void setType(int typeId) {
        int bitmapId = 0;
        switch (typeId) {
            case HelpItemInfo.TYPE_GAME:             bitmapId = R.string.bmp_crown_01_gold;         currentItem = null;             break;
            case HelpItemInfo.TYPE_STARS:            bitmapId = R.string.bmp_star;                  currentItem = null;             break;
            case HelpItemInfo.TYPE_VIRAL_CELLS:                                                     currentItem = cell;             break;
            case HelpItemInfo.TYPE_WAVES:            bitmapId = R.string.bmp_wave;                  currentItem = null;             break;
            case HelpItemInfo.TYPE_MEMBRANE:                                                        currentItem = core;             break;
            case HelpItemInfo.TYPE_PROTECTION_LEVEL:                                                currentItem = ctrlMultiplier;   break;
            case HelpItemInfo.TYPE_ENERGY:                                                          currentItem = energyElement;    break;
            case HelpItemInfo.TYPE_DNA:                                                             currentItem = dna;              break;
            case HelpItemInfo.TYPE_DNA_ELEMENT:                                                     currentItem = dnaElement;       break;
            case HelpItemInfo.TYPE_DNA_BOMB:                                                        currentItem = dnaBombElement;   break;
            case HelpItemInfo.TYPE_BOTTLE:           bitmapId = R.string.bmp_potion_00_empty;       currentItem = null;             break;
            case HelpItemInfo.TYPE_SECRET:                                                          currentItem = txtSecret;        break;
            case HelpItemInfo.TYPE_POTION:           bitmapId = R.string.bmp_potion_05_crazy_group; currentItem = null;             break;
            case HelpItemInfo.TYPE_MARKER:                                                          currentItem = labIndex;         break;
            default:                                                                                currentItem = null;             break;
        }
        setDefaultBitmap(bitmapId); //BitmapUtils.GetBitmap(bitmapId));
        refreshCurrentStatus();
    }

    @Override
    public void setPoint(float fx, float fy) {
        super.setPoint(fx, fy);

        if (cell != null)
            cell.setPoint(fx, fy);
        if (core != null)
            core.setPoint(fx, fy + 5f * fd);
        if (ctrlMultiplier != null)
            ctrlMultiplier.setPoint(fx, fy);
        if (energyElement != null)
            energyElement.setPoint(fx, fy);
        if (dna != null)
            dna.setPoint(fx, fy);
        if (dnaElement != null)
            dnaElement.setPoint(fx, fy);
        if (dnaBombElement != null)
            dnaBombElement.setPoint(fx, fy);
        if (txtSecret != null)
            txtSecret.setPoint(fx, fy);
        if (labIndex != null)
            labIndex.setPoint(fx, fy);
    }

    @Override
    public void setAlpha(int a) {
        super.setAlpha(a);

        if (cell != null)
            cell.setAlpha(a);
        if (core != null)
            core.setAlpha(a);
        if (ctrlMultiplier != null)
            ctrlMultiplier.setAlpha(a);
        if (energyElement != null)
            energyElement.setAlpha(a);
        if (dna != null)
            dna.setAlpha(a); // fix !!!
        if (dnaElement != null)
            dnaElement.setAlpha(a);
        if (dnaBombElement != null)
            dnaBombElement.setAlpha(a);
        if (txtSecret != null)
            txtSecret.setAlpha(a);
        if (labIndex != null)
            labIndex.setAlpha(a);
    }

    //region show / hide
    @Override
    public void show() {
        super.show();

        if (cell != null)
            cell.isStarted = true;
        if (core != null)
            core.isStarted = true;
        if (ctrlMultiplier != null)
            ctrlMultiplier.show();
        if (energyElement != null)
            energyElement.show();
        if (dna != null)
            dna.show();
        if (dnaElement != null)
            dnaElement.show();
        if (dnaBombElement != null)
            dnaBombElement.show();
        if (txtSecret != null)
            txtSecret.show();
        if (labIndex != null)
            labIndex.show();
    }

    @Override
    public long hide() {
        long duration = super.hide();

        if (cell != null)
            cell.isStarted = false;
        if (core != null)
            core.isStarted = false;
        if (ctrlMultiplier != null)
            ctrlMultiplier.hide();
        if (energyElement != null)
            energyElement.hide();
        if (dna != null)
            dna.hide();
        if (dnaElement != null)
            dnaElement.hide();
        if (dnaBombElement != null)
            dnaBombElement.hide();
        if (txtSecret != null)
            txtSecret.hide();
        if (labIndex != null)
            labIndex.hide();
        return duration;
    }
    //endregion

    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);
        if (currentItem != null)
            currentItem.drawFrame(c);
    }

    @Override
    public void recycle() {
        super.recycle();

        if (cell != null)
            cell.recycle();
        if (core != null)
            core.recycle();
        if (ctrlMultiplier != null)
            ctrlMultiplier.recycle();
        if (energyElement != null)
            energyElement.recycle();
        if (dna != null)
            dna.recycle();
        if (dnaElement != null)
            dnaElement.recycle();
        if (dnaBombElement != null)
            dnaBombElement.recycle();
        if (currentItem != null)
            currentItem.recycle();
        if (txtSecret != null)
            txtSecret.recycle();
        if (labIndex != null)
            labIndex.recycle();

        cell = null;
        core = null;
        ctrlMultiplier = null;
        energyElement = null;
        dna = null;
        dnaElement = null;
        dnaBombElement = null;
        txtSecret = null;
        labIndex = null;
        currentItem = null;
    }
}
