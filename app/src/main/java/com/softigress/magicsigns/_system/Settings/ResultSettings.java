package com.softigress.magicsigns._system.Settings;

import android.content.SharedPreferences;

import com.softigress.magicsigns._system.Settings._base.SettingBase;
import com.softigress.magicsigns._system.Settings._base.SettingsBase;

public class ResultSettings extends SettingsBase {

    private SettingBase<Integer> resultStars;
    private SettingBase<Integer> resultGames;
    private SettingBase<Integer> resultSigns_01_Simple;
    private SettingBase<Integer> resultSigns_02_Normal;
    private SettingBase<Integer> resultSigns_03_Hard;
    private SettingBase<Integer> resultSigns_04_Insane;
    private SettingBase<Integer> resultSigns_05_Crazy;

    public ResultSettings(SharedPreferences sp, SharedPreferences.Editor editor) {
        super(sp, editor);

        this.resultStars = new SettingBase<>("user_result_stars", 0);
        this.resultGames = new SettingBase<>("user_result_games", 0);
        this.resultSigns_01_Simple = new SettingBase<>("user_result_signs_01", 0);
        this.resultSigns_02_Normal = new SettingBase<>("user_result_signs_02", 0);
        this.resultSigns_03_Hard = new SettingBase<>("user_result_signs_03", 0);
        this.resultSigns_04_Insane = new SettingBase<>("user_result_signs_04", 0);
        this.resultSigns_05_Crazy = new SettingBase<>("user_result_signs_05", 0);
    }

    public long getHash() {
        return  resultStars.getHash() +
                resultGames.getHash() +
                resultSigns_01_Simple.getHash() +
                resultSigns_02_Normal.getHash() +
                resultSigns_03_Hard.getHash() +
                resultSigns_04_Insane.getHash() +
                resultSigns_05_Crazy.getHash();
    }

    public void reset() {
        resultStars.v = 0;
        resultGames.v = 0; // do not reset?
        resultSigns_01_Simple.v = 0;
        resultSigns_02_Normal.v = 0;
        resultSigns_03_Hard.v = 0;
        resultSigns_04_Insane.v = 0;
        resultSigns_05_Crazy.v = 0;
    }

    public Integer getStars() { return resultStars.v; }
    public void setStars(int stars) { resultStars.v = stars; }

    public Integer getGames() { return resultGames.v; }
    public void setGames(int games) { resultGames.v = games; }

    public Integer getSigns_01_Simple() { return resultSigns_01_Simple.v; }
    public void setSigns_01_Simple(int signs) { resultSigns_01_Simple.v = signs; }
    public Integer getSigns_02_Normal() { return resultSigns_02_Normal.v; }
    public void setSigns_02_Normal(int signs) { resultSigns_02_Normal.v = signs; }
    public Integer getSigns_03_Hard() { return resultSigns_03_Hard.v; }
    public void setSigns_03_Hard(int signs) { resultSigns_03_Hard.v = signs; }
    public Integer getSigns_04_Insane() { return resultSigns_04_Insane.v; }
    public void setSigns_04_Insane(int signs) { resultSigns_04_Insane.v = signs; }
    public Integer getSigns_05_Crazy() { return resultSigns_05_Crazy.v; }
    public void setSigns_05_Crazy(int signs) { resultSigns_05_Crazy.v = signs; }

    public void load() {
        loadVals(new SettingBase[]{
                this.resultStars,
                this.resultGames,
                this.resultSigns_01_Simple,
                this.resultSigns_02_Normal,
                this.resultSigns_03_Hard,
                this.resultSigns_04_Insane,
                this.resultSigns_05_Crazy,
        });
    }

    public void save() {
        saveVals(new SettingBase[]{
                this.resultStars,
                this.resultGames,
                this.resultSigns_01_Simple,
                this.resultSigns_02_Normal,
                this.resultSigns_03_Hard,
                this.resultSigns_04_Insane,
                this.resultSigns_05_Crazy,
        });
    }

    public void recycle() {
        super.recycle();

        if (resultStars != null)
            resultStars.recycle();
        if (resultGames != null)
            resultGames.recycle();

        if (resultSigns_01_Simple != null)
            resultSigns_01_Simple.recycle();
        if (resultSigns_02_Normal != null)
            resultSigns_02_Normal.recycle();
        if (resultSigns_03_Hard != null)
            resultSigns_03_Hard.recycle();
        if (resultSigns_04_Insane != null)
            resultSigns_04_Insane.recycle();
        if (resultSigns_05_Crazy != null)
            resultSigns_05_Crazy.recycle();

        resultStars = null;
        resultGames = null;
        resultSigns_01_Simple = null;
        resultSigns_02_Normal = null;
        resultSigns_03_Hard = null;
        resultSigns_04_Insane = null;
        resultSigns_05_Crazy = null;
    }
}