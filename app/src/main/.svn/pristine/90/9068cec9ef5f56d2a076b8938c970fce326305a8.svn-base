package com.softigress.magicsigns.UI.Rating.RatingRow;

import androidx.annotation.NonNull;

import com.softigress.magicsigns._Base.IRecycle;
import com.softigress.magicsigns._system.FireBase.DataBase.UserScoreInfo;

public class UserRatingRowInfo implements Comparable<UserRatingRowInfo>, IRecycle {

    public Integer index = 0;
    public final String id;
    public final UserScoreInfo userScoreInfo;

    public UserRatingRowInfo(String id, UserScoreInfo info) {
        this.id = id;
        this.userScoreInfo = info;
    }

    @Override
    public int compareTo(@NonNull UserRatingRowInfo another) {
        if (userScoreInfo == null)
            return 1;
        else if (another == null)
            return -1;
        else
            return userScoreInfo.compareTo(another.userScoreInfo);
    }

    public void recycle() {
        if (userScoreInfo != null)
            userScoreInfo.recycle();
    }
}
