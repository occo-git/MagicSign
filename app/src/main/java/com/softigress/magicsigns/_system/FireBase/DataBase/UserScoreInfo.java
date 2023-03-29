package com.softigress.magicsigns._system.FireBase.DataBase;

import androidx.annotation.NonNull;

import com.softigress.magicsigns.Game.Signs.SSequence.SStatistics.SStatistics;
import com.softigress.magicsigns.UI._base.Controls.Achievements.AchievementType;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.Utils;

public class UserScoreInfo implements Comparable<UserScoreInfo> {

    public String provider; // провайдер
    public String firebaseId; // сылка на предыдущий аккаунт firebase, при логине в google, facebook и т.д.

    public String name;
    public int score; // максимальное количество собранных очков
    public int waves; // максимальная пройденная волна
    public int games; // игр сыграно
    public long duration; // время проведенное в игре
    public long ticks; // время сохранения последнего результата
    public int dnas; // количество заряженных элементов днк

    private int signs01; // количество signs типа SIMPLE
    private int signs02; // количество signs типа NORMAL
    private int signs03; // количество signs типа HARD
    private int signs04; // количество signs типа INSANE
    private int signs05; // количество signs типа CRAZY
    private int signsGroup01; // количество signs group типа SIMPLE
    private int signsGroup02; // количество signs group типа NORMAL
    private int signsGroup03; // количество signs group типа HARD
    private int signsGroup04; // количество signs group типа INSANE
    private int signsGroup05; // количество signs group типа CRAZY

    private boolean potion01;
    private boolean potion02;
    private boolean potion03;
    private boolean potion04;
    private boolean potion05;
    private boolean groupPotion01;
    private boolean groupPotion02;
    private boolean groupPotion03;
    private boolean groupPotion04;
    private boolean groupPotion05;
    private boolean potionFinal;

    public Integer getSigns01() { return signs01; }
    public Integer getSigns02() { return signs02; }
    public Integer getSigns03() { return signs03; }
    public Integer getSigns04() { return signs04; }
    public Integer getSigns05() { return signs05; }
    public Integer getSignsGroup01() { return signsGroup01; }
    public Integer getSignsGroup02() { return signsGroup02; }
    public Integer getSignsGroup03() { return signsGroup03; }
    public Integer getSignsGroup04() { return signsGroup04; }
    public Integer getSignsGroup05() { return signsGroup05; }

    public Boolean isPotion01() { return potion01; }
    public Boolean isPotion02() { return potion02; }
    public Boolean isPotion03() { return potion03; }
    public Boolean isPotion04() { return potion04; }
    public Boolean isPotion05() { return potion05; }
    public Boolean isGroupPotion01() { return groupPotion01; }
    public Boolean isGroupPotion02() { return groupPotion02; }
    public Boolean isGroupPotion03() { return groupPotion03; }
    public Boolean isGroupPotion04() { return groupPotion04; }
    public Boolean isGroupPotion05() { return groupPotion05; }
    public boolean allPotions() {
        return potion01 && potion02 && potion03 && potion04 && potion05 &&
                groupPotion01 && groupPotion02 && groupPotion03 && groupPotion04 && groupPotion05;
    }
    public Boolean isPotionFinal() { return potionFinal; }

    public void setPotionFinal(boolean potionFinal) { this.potionFinal = potionFinal; }
    public void setPotionEnabled(AchievementType type, boolean enabled) {
        switch (type) {
            case SIMPLE_01:         potion01 = enabled; break;
            case NORMAL_02:         potion02 = enabled; break;
            case HARD_03:           potion03 = enabled; break;
            case INSANE_04:         potion04 = enabled; break;
            case CRAZY_05:          potion05 = enabled; break;
            case SIMPLE_01_GROUP:   groupPotion01 = enabled; break;
            case NORMAL_02_GROUP:   groupPotion02 = enabled; break;
            case HARD_03_GROUP:     groupPotion03 = enabled; break;
            case INSANE_04_GROUP:   groupPotion04 = enabled; break;
            case CRAZY_05_GROUP:    groupPotion05 = enabled; break;
            default:
        }
    }

    public void upGames(long currentGameTicks) {
        this.games++;
        this.duration += currentGameTicks;
    }

    public void setStatistics(SStatistics sStatistics) {
        if ( sStatistics != null) {
            signs01 = sStatistics.signs_01_Simple;
            signs02 = sStatistics.signs_02_Normal;
            signs03 = sStatistics.signs_03_Hard;
            signs04 = sStatistics.signs_04_Insane;
            signs05 = sStatistics.signs_05_Crazy;
            signsGroup01 = sStatistics.signs_group_01_Simple;
            signsGroup02 = sStatistics.signs_group_02_Normal;
            signsGroup03 = sStatistics.signs_group_03_Hard;
            signsGroup04 = sStatistics.signs_group_04_Insane;
            signsGroup05 = sStatistics.signs_group_05_Crazy;

            potion01 = sStatistics.potion_01_Simple;
            potion02 = sStatistics.potion_02_Normal;
            potion03 = sStatistics.potion_03_Hard;
            potion04 = sStatistics.potion_04_Insane;
            potion05 = sStatistics.potion_05_Crazy;
            groupPotion01 = sStatistics.potion_01_Simple_group;
            groupPotion02 = sStatistics.potion_02_Normal_group;
            groupPotion03 = sStatistics.potion_03_Hard_group;
            groupPotion04 = sStatistics.potion_04_Insane_group;
            groupPotion05 = sStatistics.potion_05_Crazy_group;

            // еще раз гарантированно апдейтим полученные эликсиры
            if (signs01 >= CurrentSettings.potionSignsCount)
                potion01 = true;
            if (signs02 >= CurrentSettings.potionSignsCount)
                potion02 = true;
            if (signs03 >= CurrentSettings.potionSignsCount)
                potion03 = true;
            if (signs04 >= CurrentSettings.potionSignsCount)
                potion04 = true;
            if (signs05 >= CurrentSettings.potionSignsCount)
                potion05 = true;
            if (signsGroup01 >= CurrentSettings.potionSignsGroupCount)
                groupPotion01 = true;
            if (signsGroup02 >= CurrentSettings.potionSignsGroupCount)
                groupPotion02 = true;
            if (signsGroup03 >= CurrentSettings.potionSignsGroupCount)
                groupPotion03 = true;
            if (signsGroup04 >= CurrentSettings.potionSignsGroupCount)
                groupPotion04 = true;
            if (signsGroup05 >= CurrentSettings.potionSignsGroupCount)
                groupPotion05 = true;
        }
    }

    public UserScoreInfo() {
        this.score = 0;
        this.waves = 0;
        this.games = 0;
        this.duration = 0;
        this.ticks = Utils.getTime();
        this.dnas = 0;
    }
    
    public UserScoreInfo(UserScoreInfo info) {
        this.score = info.score; // максимальное количество собранных очков
        this.waves = info.waves; // максимальная пройденная волна
        this.games = info.games; // игр сыграно
        this.duration = info.duration; // время проведенное в игре
        this.ticks = info.ticks; // время сохранения последнего результата
        this.dnas = info.dnas; // количество заряженых элементов днк

        this.signs01 = info.signs01; // количество signs типа SIMPLE
        this.signs02 = info.signs02; // количество signs типа NORMAL
        this.signs03 = info.signs03; // количество signs типа HARD
        this.signs04 = info.signs04; // количество signs типа INSANE
        this.signs05 = info.signs05; // количество signs типа CRAZY
        this.signsGroup01 = info.signsGroup01; // количество signs group типа SIMPLE
        this.signsGroup02 = info.signsGroup02; // количество signs group типа NORMAL
        this.signsGroup03 = info.signsGroup03; // количество signs group типа HARD
        this.signsGroup04 = info.signsGroup04; // количество signs group типа INSANE
        this.signsGroup05 = info.signsGroup05; // количество signs group типа CRAZY

        this.potion01 = info.potion01;
        this.potion02 = info.potion02;
        this.potion03 = info.potion03;
        this.potion04 = info.potion04;
        this.potion05 = info.potion05;
        this.groupPotion01 = info.groupPotion01;
        this.groupPotion02 = info.groupPotion02;
        this.groupPotion03 = info.groupPotion03;
        this.groupPotion04 = info.groupPotion04;
        this.groupPotion05 = info.groupPotion05;
        this.potionFinal = info.potionFinal;
    }

    public void reset() {
        this.score = 0;
        this.waves = 0;
        //this.games = 0;
        //this.duration = 0;
        this.ticks = Utils.getTime();
        this.dnas = 0;

        this.signs01 = 0; // количество signs типа SIMPLE
        this.signs02 = 0; // количество signs типа NORMAL
        this.signs03 = 0; // количество signs типа HARD
        this.signs04 = 0; // количество signs типа INSANE
        this.signs05 = 0; // количество signs типа CRAZY
        this.signsGroup01 = 0; // количество signs group типа SIMPLE
        this.signsGroup02 = 0; // количество signs group типа NORMAL
        this.signsGroup03 = 0; // количество signs group типа HARD
        this.signsGroup04 = 0; // количество signs group типа INSANE
        this.signsGroup05 = 0; // количество signs group типа CRAZY

        this.potion01 = false;
        this.potion02 = false;
        this.potion03 = false;
        this.potion04 = false;
        this.potion05 = false;
        this.groupPotion01 = false;
        this.groupPotion02 = false;
        this.groupPotion03 = false;
        this.groupPotion04 = false;
        this.groupPotion05 = false;
        this.potionFinal = false;
    }

    @Override
    public int compareTo(@NonNull UserScoreInfo another) {
        if (another == null)
            return -1;
        else // сравнение по количеству полученных очков
            return score > another.score ? -1 : (score == another.score ? (ticks >= another.ticks ? -1 : 1) : 1);
    }

    public boolean isAchievementEnabled(AchievementType type) {
        switch (type) {
            case SIMPLE_01:         return potion01;
            case NORMAL_02:         return potion02;
            case HARD_03:           return potion03;
            case INSANE_04:         return potion04;
            case CRAZY_05:          return potion05;
            case SIMPLE_01_GROUP:   return groupPotion01;
            case NORMAL_02_GROUP:   return groupPotion02;
            case HARD_03_GROUP:     return groupPotion03;
            case INSANE_04_GROUP:   return groupPotion04;
            case CRAZY_05_GROUP:    return groupPotion05;
            case FINAL_10:          return potionFinal;
            default:                return false;
        }
    }

    public AchievementType firstDisabledAchievementType() {
        if (!potion01)              return AchievementType.SIMPLE_01;
        else if (!potion02)         return AchievementType.NORMAL_02;
        else if (!potion03)         return AchievementType.HARD_03;
        else if (!potion04)         return AchievementType.INSANE_04;
        else if (!potion05)         return AchievementType.CRAZY_05;
        else if (!groupPotion01)         return AchievementType.SIMPLE_01_GROUP;
        else if (!groupPotion02)         return AchievementType.NORMAL_02_GROUP;
        else if (!groupPotion03)         return AchievementType.HARD_03_GROUP;
        else if (!groupPotion04)         return AchievementType.INSANE_04_GROUP;
        else if (!groupPotion05)         return AchievementType.CRAZY_05_GROUP;
        else return null;
    }

    public Integer getSignsCount(AchievementType type) {
        switch (type) {
            case SIMPLE_01:         return signs01;
            case NORMAL_02:         return signs02;
            case HARD_03:           return signs03;
            case INSANE_04:         return signs04;
            case CRAZY_05:          return signs05;
            case SIMPLE_01_GROUP:   return signsGroup01;
            case NORMAL_02_GROUP:   return signsGroup02;
            case HARD_03_GROUP:     return signsGroup03;
            case INSANE_04_GROUP:   return signsGroup04;
            case CRAZY_05_GROUP:    return signsGroup05;
            default:                return 0;
        }
    }

    public void recycle() { }
}
