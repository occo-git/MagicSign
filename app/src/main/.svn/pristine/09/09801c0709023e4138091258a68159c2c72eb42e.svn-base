package com.softigress.magicsigns._system.FireBase.DataBase;

import com.softigress.magicsigns.UI.Rating.RatingRow.ScoreInfo;
import com.softigress.magicsigns.UI.Rating.RatingRow.UserRatingRowInfo;
import com.softigress.magicsigns._Base.ArrayRecyclable;

public interface IDataBaseManagerListener {
    void onRatingChanged(ScoreInfo userScore, ArrayRecyclable<ScoreInfo> scores);
    void onTopUserInfosChanged(ArrayRecyclable<UserRatingRowInfo> topRowInfos);
    void onCurrentUserInfoChanged(UserRatingRowInfo rowInfo);
    void onCurrentUserVideoCountsChanged(UserVideoCount userVideoCount);
}
