package com.softigress.magicsigns._system.Settings._base;

import android.content.SharedPreferences;

import com.softigress.magicsigns._system.Utils.Utils;

public class SettingsBase {

    protected SharedPreferences sp;
    private SharedPreferences.Editor editor;

    protected SettingsBase(SharedPreferences sp, SharedPreferences.Editor editor) {
        this.sp = sp;
        this.editor = editor;
    }

    private void loadVal(SettingBase sb) {
        try {
            if (sp.contains(sb.n)) {
                if (sb.type == Integer.class)
                    sb.v = sp.getInt(sb.n, 0);
                if (sb.type == Float.class)
                    sb.v = sp.getFloat(sb.n, 0f);
                else if (sb.type == Long.class)
                    sb.v = sp.getLong(sb.n, 0L);
                else if (sb.type == Boolean.class)
                    sb.v = sp.getBoolean(sb.n, false);
                else if (sb.type == String.class)
                    sb.v = sp.getString(sb.n, "");
            }
        } catch (Throwable t) {
            Utils.CrashReport("SettingsBase.loadVal [" + sb.n + "]", t);
        }
    }

    protected void loadVals(SettingBase[] sbs) {
        for (SettingBase sb : sbs)
            loadVal(sb);
    }

    private void saveVal(SettingBase sb) {
        try {
            if (sb.type == Integer.class)
                editor.putInt(sb.n, (int) sb.v);
            if (sb.type == Float.class)
                editor.putFloat(sb.n, (float) sb.v);
            else if (sb.type == Long.class)
                editor.putLong(sb.n, (long) sb.v);
            else if (sb.type == Boolean.class)
                editor.putBoolean(sb.n, (boolean) sb.v);
            else if (sb.type == String.class)
                editor.putString(sb.n, (String) sb.v);
        } catch (Throwable t) {
            Utils.CrashReport("SettingsBase.saveVal [" + sb.n + "]", t);
        }
    }

    protected void saveVals(SettingBase[] sbs) {
        for (SettingBase sb : sbs)
            saveVal(sb);
    }

    public void recycle() {
        sp = null;
        editor = null;
    }
}
