package com.softigress.magicsigns._system.FireBase.Analytics;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.softigress.magicsigns._system.Utils.Utils;

public class AnalyticsManager {

    // signs events
    public static final String MS_EVENT_ACTION = "ms_event_action";
    public static final String MS_EVENT_BTN_CLICK = "ms_event_btn_click";
    public static final String MS_EVENT_CHECKER_CLICK = "ms_event_checker_click";
    public static final String MS_EVENT_DIALOG_SHOW = "ms_event_dialog_show";
    public static final String MS_EVENT_DIALOG_HIDE = "ms_event_dialog_hide";
    public static final String MS_EVENT_WAVE = "ms_event_wave";     // старт волны в игре
    public static final String MS_EVENT_BONUS = "ms_event_bonus";   // получение бонуса в игре
    public static final String MS_EVENT_ACHIEVEMENT = "ms_event_achievement"; // получение достижения в игре
    public static final String MS_EVENT_AD_VIDEO = "ms_event_ad_video"; // просмотр рекламного видео для получения эликсира
    public static final String MS_EVENT_SHARE = "ms_event_share"; // поделиться

    // user properties
    public static final String MS_UP_WAVE = "ms_up_wave";           // значение достигнутой волны в игре
    public static final String MS_UP_WON = "ms_up_won";             // результат игры (пройгрыш/выйгрыш)
    public static final String MS_UP_RATED = "ms_up_rated";
    public static final String MS_UP_SOUND = "ms_up_sound";
    public static final String MS_UP_SOUND_VOL = "ms_up_sound_vol";
    public static final String MS_UP_MUSIC = "ms_up_music";
    public static final String MS_UP_MUSIC_VOL = "ms_up_music_vol";
    public static final String MS_UP_VIBR = "ms_up_vibr";

    private static final String MS_VAL_TRUE = "true";
    private static final String MS_VAL_FALSE = "false";

    private final FirebaseAnalytics analytics;

    public AnalyticsManager(Activity activity) {
        analytics = FirebaseAnalytics.getInstance(activity);
    }

    public void logEvent(String event, String itemName) {
        try {
            if (analytics != null)
                if (event != null && itemName != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
                    analytics.logEvent(event, bundle);
                }
        } catch (Throwable t) {
            Utils.ToastError("log event", t);
        }
    }

    public void setUserProperty(String name, String val) {
        if (analytics != null)
            analytics.setUserProperty(name, val);
    }
    public void setUserProperty(String name, boolean val) {
        if (analytics != null)
            analytics.setUserProperty(name, val ? MS_VAL_TRUE : MS_VAL_FALSE);
    }

    public void recycle() { }
}
