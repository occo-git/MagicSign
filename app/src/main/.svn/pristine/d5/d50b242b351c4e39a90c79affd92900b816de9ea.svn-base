package com.softigress.magicsigns.Activities.AdActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Controls.Achievements.AchievementType;
import com.softigress.magicsigns._system.Utils.AdUtils;
import com.softigress.magicsigns._system.Utils.Utils;

public class VideoAdActivity extends Activity {

    public static final int REQUEST_VIDEO_REWARD = 1001;
    public static final String ACHIEVEMENT_TYPE = "AchievementType";
    private static final String REWARD = "reward";

    private RewardedVideoAd mRewardedVideoAd; // видео
    private AchievementType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        /*Button btnAd = (Button) findViewById(R.id.btnAd);
        btnAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd();
            }
        });*/

        type = (AchievementType) getIntent().getSerializableExtra(ACHIEVEMENT_TYPE);
        String ad_id = getID(type); // определяем ID рекламного видео

        MobileAds.initialize(getApplicationContext(), ad_id);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(
                new RewardedVideoAdListener() {
                    @Override public void onRewardedVideoAdLoaded() { showAd(); }
                    @Override public void onRewardedVideoAdOpened() { }
                    @Override public void onRewardedVideoStarted() { }
                    @Override public void onRewardedVideoAdClosed() { closeAd(); }
                    @Override public void onRewarded(RewardItem rewardItem) {
                        // получить награду
                        int reward = rewardItem.getAmount();
                        if (reward > 0) {
                            Intent intent = new Intent();
                            intent.putExtra(REWARD, reward);
                            intent.putExtra(ACHIEVEMENT_TYPE, type);
                            setResult(RESULT_OK, intent);
                        }
                    }
                    @Override public void onRewardedVideoAdLeftApplication() { }
                    @Override public void onRewardedVideoAdFailedToLoad(int errorCode) {
                        String err = AdUtils.getRequestErrorString(errorCode);
                    }
                    @Override public void onRewardedVideoCompleted() { }
                }
        );
        // loadAd
        if (mRewardedVideoAd != null)
            mRewardedVideoAd.loadAd(ad_id, AdUtils.getRequest());
    }

    private String getID(AchievementType type) {
        /*return type != null ? getString(R.string.rewarded_video_ad_unit_id) // видео с вознаграждением
                            : getString(R.string.no_rewarded_video_ad_unit_id); // видео без вознаграждения
        */
        return AdUtils.rewarded_video_ad_test_id; // тестовое видео
    }

    private void showAd() {
        try {
            if (mRewardedVideoAd != null && mRewardedVideoAd.isLoaded())
                mRewardedVideoAd.show();
            //else
            //    Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        catch (Throwable t) {
            Utils.CrashReport("VideoAdActivity.showAd", t);
        }
    }

    private void closeAd() {
        finish(); // закроем Activity
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

        } catch (Throwable t) {
            Utils.CrashReport("VideoAdActivity.onActivityResult", t);
        }
    }*/

    //region lifestyle events
    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
    //endregion
}
