package com.softigress.magicsigns._system.Settings;

import android.content.SharedPreferences;

public class SettingsManager {

    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;

    public final UserSettings userSettings;

    public SettingsManager(SharedPreferences sp) {
        this.sp = sp;
        this.editor = sp.edit();

        this.userSettings = new UserSettings(sp, editor);
        reset(); //
    }

    private long getHash() {
        long hash = 0;
        return hash + userSettings.getHash();
    }

    public void reset() {
        userSettings.reset();
    }

    public boolean load() {
        userSettings.load();

        //long time = userSettings.getTime();
        //long hash = getHash();
        return true;//userSettings.getTime() == getHash();
    }

    public void save() {
        //Utils.Toast("begin: save settings");

        //userSettings.setTime(getHash());
        userSettings.save();

        editor.apply();
        //Utils.Toast("end: save settings");
    }

    public void recycle() {
        if (userSettings != null)
            userSettings.recycle();
    }
}
