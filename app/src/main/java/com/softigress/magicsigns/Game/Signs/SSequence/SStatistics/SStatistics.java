package com.softigress.magicsigns.Game.Signs.SSequence.SStatistics;

import com.softigress.magicsigns.UI._base.Controls.Achievements.AchievementType;
import com.softigress.magicsigns._system.FireBase.DataBase.UserScoreInfo;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Settings.Infos.SignInfo;
import com.softigress.magicsigns._system.Settings.Infos.SignStrength;
import com.softigress.magicsigns._system.Utils.Utils;

public class SStatistics {

    public int signs_01_Simple = 0;
    public int signs_02_Normal = 0;
    public int signs_03_Hard = 0;
    public int signs_04_Insane = 0;
    public int signs_05_Crazy = 0;
    public int signs_group_01_Simple = 0;
    public int signs_group_02_Normal = 0;
    public int signs_group_03_Hard = 0;
    public int signs_group_04_Insane = 0;
    public int signs_group_05_Crazy = 0;

    public boolean potion_01_Simple = false;
    public boolean potion_02_Normal = false;
    public boolean potion_03_Hard = false;
    public boolean potion_04_Insane = false;
    public boolean potion_05_Crazy = false;
    public boolean potion_01_Simple_group = false;
    public boolean potion_02_Normal_group = false;
    public boolean potion_03_Hard_group = false;
    public boolean potion_04_Insane_group = false;
    public boolean potion_05_Crazy_group = false;

    public boolean isEnabled = false;

    public SStatistics() {}

    private ISStatisticsListener listener;
    public void setListener(ISStatisticsListener l) { listener = l; }

    public void init() {
        UserScoreInfo usi = Utils.dataBaseManager.currentUserScoreInfo;
        if (usi != null) {
            signs_01_Simple = usi.getSigns01();
            signs_02_Normal = usi.getSigns02();
            signs_03_Hard = usi.getSigns03();
            signs_04_Insane = usi.getSigns04();
            signs_05_Crazy = usi.getSigns05();
            signs_group_01_Simple = usi.getSignsGroup01();
            signs_group_02_Normal = usi.getSignsGroup02();
            signs_group_03_Hard = usi.getSignsGroup03();
            signs_group_04_Insane = usi.getSignsGroup04();
            signs_group_05_Crazy = usi.getSignsGroup05();

            potion_01_Simple = usi.isPotion01();
            potion_02_Normal = usi.isPotion02();
            potion_03_Hard = usi.isPotion03();
            potion_04_Insane = usi.isPotion04();
            potion_05_Crazy = usi.isPotion05();
            potion_01_Simple_group = usi.isGroupPotion01();
            potion_02_Normal_group = usi.isGroupPotion02();
            potion_03_Hard_group = usi.isGroupPotion03();
            potion_04_Insane_group = usi.isGroupPotion04();
            potion_05_Crazy_group = usi.isGroupPotion05();
        }
    }

    public void upSignInfo(SignInfo info, boolean isGroup) {
        if (isEnabled) {
            SignStrength strength = info.strength;
            if (isGroup) {
                //region grouped signs
                switch (info.strength) {
                    case SIMPLE:
                        signs_group_01_Simple++;
                        if (!potion_01_Simple_group && signs_group_01_Simple >= CurrentSettings.potionSignsGroupCount) {
                            potion_01_Simple_group = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.SIMPLE_01_GROUP);
                        }
                        break;
                    case NORMAL:
                        signs_group_02_Normal++;
                        if (!potion_02_Normal_group && signs_group_02_Normal >= CurrentSettings.potionSignsGroupCount) {
                            potion_02_Normal_group = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.NORMAL_02_GROUP);
                        }
                        break;
                    case HARD:
                        signs_group_03_Hard++;
                        if (!potion_03_Hard_group && signs_group_03_Hard >= CurrentSettings.potionSignsGroupCount) {
                            potion_03_Hard_group = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.HARD_03_GROUP);
                        }
                        break;
                    case INSANE:
                        signs_group_04_Insane++;
                        if (!potion_04_Insane_group && signs_group_04_Insane >= CurrentSettings.potionSignsGroupCount) {
                            potion_04_Insane_group = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.INSANE_04_GROUP);
                        }
                        break;
                    case CRAZY:
                        signs_group_05_Crazy++;
                        if (!potion_05_Crazy_group && signs_group_05_Crazy >= CurrentSettings.potionSignsGroupCount) {
                            potion_05_Crazy_group = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.CRAZY_05_GROUP);
                        }
                        break;
                }
                //endregion
            } else {
                //region signs
                switch (strength) {
                    case SIMPLE:
                        signs_01_Simple++;
                        if (!potion_01_Simple && signs_01_Simple >= CurrentSettings.potionSignsCount) {
                            potion_01_Simple = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.SIMPLE_01);
                        }
                        break;
                    case NORMAL:
                        signs_02_Normal++;
                        if (!potion_02_Normal && signs_02_Normal >= CurrentSettings.potionSignsCount) {
                            potion_02_Normal = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.NORMAL_02);
                        }
                        break;
                    case HARD:
                        signs_03_Hard++;
                        if (!potion_03_Hard && signs_03_Hard >= CurrentSettings.potionSignsCount) {
                            potion_03_Hard = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.HARD_03);
                        }
                        break;
                    case INSANE:
                        signs_04_Insane++;
                        if (!potion_04_Insane && signs_04_Insane >= CurrentSettings.potionSignsCount) {
                            potion_04_Insane = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.INSANE_04);
                        }
                        break;
                    case CRAZY:
                        signs_05_Crazy++;
                        if (!potion_05_Crazy && signs_05_Crazy >= CurrentSettings.potionSignsCount) {
                            potion_05_Crazy = true;
                            if (listener != null)
                                listener.handleOnAchievement(AchievementType.CRAZY_05);
                        }
                        break;
                    default:
                        break;
                }
                //endregion
            }
        }
    }
}
