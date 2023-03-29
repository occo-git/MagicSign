package com.softigress.magicsigns.Activities.MainActivity;

import com.softigress.magicsigns.UI._Main.Dialogs.Login.IGetNameListener;
import com.softigress.magicsigns.UI._base.Controls.Achievements.AchievementType;
import com.softigress.magicsigns.UI._base.Groups.Dialogs.GrpDialog;
import com.softigress.magicsigns._system.FireBase.RemoteConfig.FileInfos.FileInfo;

interface IMainListener {
    void handleOnToast(String message);
    void handleOnInitAd();

    void handleOnShowIntro();
    void handleOnShowMain();
    void handleOnShowRating();
    void handleOnShowLab();
    void handleOnStartGame();
    void handleOnPause(GrpDialog dlg);
    void handleOnResume();
    void handleOnResetGame();
    void handleOnExitGame();

    void handleConnectionOff();
    void handleConnectionOn();
    void handleConnection(boolean isOnOff);
    void handleAuthChanged();

    void handleShareScreenshot();
    void handleShareLink();
    void handleRateGame(int stars);
    void handleBrowseUrl(String url);
    void handleGetName(IGetNameListener getNameListener);

    void handleStartLoadMusic(int musicId);
    void handleSetLoadMusicProgress(FileInfo fileInfo, float progress);

    void handleShowInterstitialAd();
    void handleShowVideoAd(AchievementType type);
}