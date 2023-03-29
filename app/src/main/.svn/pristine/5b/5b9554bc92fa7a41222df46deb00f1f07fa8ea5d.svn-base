package com.softigress.magicsigns.UI._base.Controls._base.Texts;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._system.Utils.Utils;

public class MessageInfo {

    public final static int MSG_TEMPER_DOWN = -1;
    public final static int MSG_TEMPER_OK = 0;
    public final static int MSG_TEMPER_UP = 1;

    public String type = null; // тип сообщений (уникальный для группы сообщений)
    public String message = null;
    public boolean isIdea = false;
    private int temperId = MSG_TEMPER_OK;

    public MessageInfo(String type, int messageId, boolean isIdea, int temperId) {
        this.type = type;
        this.message = Utils.getRes(messageId);
        this.isIdea = isIdea;
        this.temperId = temperId;
    }

    public static MessageInfo[] getMessages(String type, int[] info) {
        MessageInfo[] mis = new MessageInfo[info.length / 3];
        int i = 0;
        int m = 0;
        while (i < info.length) {
            boolean isIdea = info[i++] > 0;
            int temperId = info[i++];
            int messageId = info[i++];
            mis[m++] = new MessageInfo(type, messageId, isIdea, temperId);
        }
        return mis;
    }

    public static MessageInfo getRandomMessage(MessageInfo[] mis) {
        if (mis != null && mis.length > 0)
            return mis[Utils.getRandom(mis.length)];
        return null;
    }

    public void playMessageSound() { Utils.playSound(getSoundId()); }

    private int getSoundId() {
        switch (temperId) {
            case MSG_TEMPER_DOWN: return R.raw.message_down;
            case MSG_TEMPER_OK: return R.raw.message_ok;
            case MSG_TEMPER_UP: return R.raw.message_up;
            default: return R.raw.message_ok;
        }
    }
}
