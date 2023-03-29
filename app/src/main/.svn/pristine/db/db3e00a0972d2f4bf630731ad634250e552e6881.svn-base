package com.softigress.magicsigns.UI.Rating.RatingRow;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.SystemClock;
import androidx.annotation.Keep;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns.UI._base.Controls.Crown.DrawingCrown;
import com.softigress.magicsigns.UI._base.Controls.Crown.DrawingCrownSmall;
import com.softigress.magicsigns.UI._base.Controls._base.Texts.DrawingText;
import com.softigress.magicsigns.UI._base.Effects.Glares.Glares;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingVAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBaseTouchable;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.TextUtils;

public class CtrlRatingRow extends DrawingBaseTouchable {

    private static final float xPotionOffset = .04f;
    private static final float xStarOffset = .075f;
    private static final float xOrdenOffset = .05f;
    private static final float xIndexOffset = .14f;
    private static final float xCrownOffset = .14f;
    private static final float xNameOffset = .20f;
    private static final float xScoreOffset = .09f;

    //private DrawingBase back;
    private DrawingBase star;

    private float waveProgress = 0;
    private DrawingText txtIndex;
    private DrawingText txtName;
    private DrawingText txtScore;
    private DrawingCrownSmall crown;
    private Glares glares;
    private DrawingBase orden;
    private DrawingBase potion;

    private Paint paint1;
    private Paint paint0;
    private Paint paintCurrent0;
    //private Paint p1;
    private Path path = new Path();

    private UserRatingRowInfo info;
    private boolean isCurrentUser = false;
    //private RatingRowSignsCounter signsCounter;

    public CtrlRatingRow(float fy, float fh) {
        super(0f, fy, 1f, fh);
        setAlign(DrawingHAlign.LEFT, DrawingVAlign.TOP);

        paint1 = PaintUtils.getPaint(alpha / 16, 255, 255, 255);
        paint0 = PaintUtils.getPaintStroke(alpha, 32, 32, 32, PaintUtils.strokeWidth2);
        paintCurrent0 = PaintUtils.getPaintStroke(alpha, 96, 96, 96, PaintUtils.strokeWidth2);

        //float sx =  fx + .5f * fw;
        float y0 = fy + .19f * fh;
        float sy =  fy + .5f * fh;
        float y1 = fy + .62f * fh;

        potion = new DrawingBase(.9f * fh, R.string.bmp_potion_10_final);
        potion.setPoint(fx + fw - xPotionOffset, fy + .38f * fh);
        potion.hide();

        //back = new DrawingBase(1.25f * fh, R.drawable.item_back);
        //back.setPoint(fx + fw, sy);
        //back.setAlpha((int)(.75 * 255));
        star = new DrawingBase(.25f * fh, R.string.bmp_star);
        star.setPoint(fx + fw - xStarOffset, y0);

        crown = new DrawingCrownSmall(fx + xCrownOffset, sy, .8f * fh);

        orden = new DrawingBase(1.4f * fh, R.drawable.orden);
        orden.setPoint(fx + xOrdenOffset, sy);
        orden.hide();

        txtIndex = new DrawingText(DrawingHAlign.CENTER, TextUtils.rating_row_index);
        //txtIndex.setTextBack(3f, 20, 255, 255, 255);
        txtIndex.setTextBack(3f, 64, 0, 0, 0);
        txtIndex.setPoint(fx + xIndexOffset, y1);
        txtIndex.setVerticalAlign(DrawingVAlign.BOTTOM);

        txtName = new DrawingText(DrawingHAlign.LEFT, Typeface.DEFAULT, TextUtils.rating_row_name);
        txtName.setTextBack(3f, 32, 0, 0, 0);
        txtName.setPoint(fx + xNameOffset, y1);
        txtName.setVerticalAlign(DrawingVAlign.BOTTOM);

        txtScore = new DrawingText(DrawingHAlign.RIGHT, TextUtils.rating_row_score);
        //txtScore.setTextBack(3f, 20, 255, 255, 255);
        txtScore.setTextBack(3f, 64, 0, 0, 0);
        txtScore.setPoint(fx + fw - xScoreOffset, y1);
        txtScore.setVerticalAlign(DrawingVAlign.BOTTOM);

        //signsCounter = new RatingRowSignsCounter(fx, fy, fh);

        //glares = new Glares(xCrown, sy, .075f, .025f, 3);
        glares = new Glares(fx + fw / 2f, fy + fh / 2f, fw, fh, .0333f, 3);
    }

    public void setFontSize(float size) {
        txtIndex.setFontSize(size);
        txtName.setFontSize(size);
        txtScore.setFontSize(size);
    }

    @Override
    public void setAlpha(int a) {
        super.setAlpha(a);

        //paint1.setAlpha(a / 16);
        paint0.setAlpha(a);
        paintCurrent0.setAlpha(a);

        potion.setAlpha(a);
        //back.setAlpha((int)(.75 * 255));
        star.setAlpha(a);
        crown.setAlpha(a);
        orden.setAlpha(a);

        txtIndex.setAlpha(a);
        txtName.setAlpha(a);
        txtScore.setAlpha(a);
    }

    public void reset() {
        txtIndex.setText("");
        txtName.setText("");
        txtScore.setText("");
        //crown.hide();
    }

    private void setCurrentUser(boolean isCurrentUser) {
        if (this.isCurrentUser != isCurrentUser) {
            this.isCurrentUser = isCurrentUser;
            onChange();
        }
        if (isCurrentUser)
            glares.show();
        else
            glares.hide();
    }

    private void setWaveProgress(float waveProgress) {
        if (this.waveProgress != waveProgress) {
            this.waveProgress = waveProgress;
            onChange();
        }
    }

    public void setUserRatingRowInfo(UserRatingRowInfo info, boolean isCurrentUser) {
        this.info = info;
        if (info != null) {
            setCurrentUser(isCurrentUser);
            txtIndex.setTextBackARGB(isCurrentUser ? 128 : 64, 0, 0, 0);
            txtName.setTextBackARGB(isCurrentUser ? 128 : 64, 0, 0, 0);
            txtScore.setTextBackARGB(isCurrentUser ? 128 : 64, 0, 0, 0);
            if (info.userScoreInfo != null) {
                //signsCounter.setInfo(info.userScoreInfo);
                //signsCounter.setAlpha(isCurrentUser ? 64 : 64);
                setWaveProgress(info.userScoreInfo.waves / (float)CurrentSettings.signWavesCount);
                Integer score = info.userScoreInfo.score;
                txtName.setText(info.userScoreInfo.name);
                if (score > 0) {
                    txtScore.setText(score.toString());
                    if (info.index > 0) {
                        txtIndex.setFontSize(TextUtils.rating_row_index * TextUtils.getFontSizeK(info.index));
                        txtIndex.setText(info.index.toString());
                        if (info.index < 4)
                            txtIndex.hide();
                        else
                            txtIndex.show();

                        // test
                        /*if (isCurrentUser) {
                            Integer ind = 4444444;
                            txtIndex.setFontSize(TextUtils.rating_row_index * TextUtils.getFontSizeK(ind));
                            txtIndex.setText(ind.toString());
                        }*/
                    }
                } else {
                    txtScore.setText("");
                    txtIndex.setText("");
                }
                if (waveProgress >= 1)
                    orden.show();
                else
                    orden.hide();
                if (info.userScoreInfo.isPotionFinal())
                    potion.show();
                else
                    potion.hide();
            }

            if (crown != null) {
                switch (info.index) {
                    case 1: crown.setStatus(DrawingCrown.CROWN_GOLD); break;
                    case 2: crown.setStatus(DrawingCrown.CROWN_SILVER); break;
                    case 3: crown.setStatus(DrawingCrown.CROWN_BRONZE); break;
                    case 4:
                    case 5:
                    case 6:
                    case 7: crown.hide();//.setStatus(DrawingCrown.CROWN_GLASS); break;
                    // default: crown.hide();
                }
            }
        }
    }
    public UserRatingRowInfo getUserRatingRowInfo() { return info; }

    //region Touch
    private static final int maxTouchedAlpha = 100;
    private int currentTouchedAlpha = 0;
    @Keep
    private void setCurrentTouchedAlpha(int a) {
        if (currentTouchedAlpha != a) {
            currentTouchedAlpha = a;
            onChange();
        }
    }
    private static final int unTouchedDuration = 200;
    private ObjectAnimator touchedAnim = null;
    @Override
    protected void onTouchChanged() {
        if (touchedAnim != null && touchedAnim.isStarted()) {
            touchedAnim.cancel();
            touchedAnim = null;
        }
        if (isOnTouch)
            setCurrentTouchedAlpha(maxTouchedAlpha);
        else {
            int a = currentTouchedAlpha;
            touchedAnim = ObjectAnimator.ofInt(this, "currentTouchedAlpha", a, 0).setDuration(unTouchedDuration);
            touchedAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            touchedAnim.start();
        }
    }
    //endregion

    private float highlightK = 0f;
    private static final int highlightStep = 10000;
    private static final int highlightDuration = 4500;
    private long highlightTicks = 0;
    private void drawHighlight(Canvas c) {

        long ticks = SystemClock.elapsedRealtime();
        if (highlightTicks == 0)
            highlightTicks = ticks;
        long delta = ticks - highlightTicks;
        highlightK = (float) delta / highlightDuration;
        if (delta < highlightDuration) {
            //float aK = highlightK;
            paint1.setAlpha(8 + (int)(highlightK * 16));
            float k = - .5f + 2f * highlightK;
            float ww = w / 30;
            float yy = y + h;

            float x1 = x + k * w;
            path.reset();
            path.moveTo(x1 + ww, y);
            path.lineTo(x1 - ww, y);
            path.lineTo(x1 - ww - h, yy);
            path.lineTo(x1 + ww - h, yy);
            c.drawPath(path, paint1);

            float x2 = x + (1 - k) * w;
            path.reset();
            path.moveTo(x2 + ww + h, y);
            path.lineTo(x2 - ww + h, y);
            path.lineTo(x2 - ww, yy);
            path.lineTo(x2 + ww, yy);
            c.drawPath(path, paint1);
        } else {
            if (delta > highlightStep) {
                highlightTicks = 0;
                highlightK = 0f;
            }
        }
    }

    //region Gradients
    @Override
    protected void onChange() {
        calc();
        onGradientChange();
    }

    private LinearGradient gradientWave, gradient0, gradient1, gradientTouched;
    private void onGradientChange() {
        float aa = alpha / 255f;
        float dw = .2f * w;
        float x0 = x - dw;
        float x1 = x + w + dw;
        float y1 = y + h;
        gradientWave = getGradient(0, y, MetrixUtils.screen_metrix_width * waveProgress, y, 0, 255, 96 * aa, 255);
        if (isCurrentUser) {
            float y3 = y + h / 3;
            gradient0 = getGradient(x0, y, x1, y1, 50 * aa, 160, 255);
            gradient1 = getGradient(x0, y, x1, y3, 40 * aa, 255, 20 * aa, 255);
        } else {
            gradient0 = getGradient(x0, y, x0, y1, 150 * aa, 64, 40);
        }
        gradientTouched = getGradient(x0, y, x1, y1, currentTouchedAlpha * aa, 160, 255);
    }

    private LinearGradient getGradient(float x0, float y0, float x1, float y1, float a0, int c0, float a1, int c1) {
        return new LinearGradient(x0, y0, x1, y1, Color.argb((int)a0, c0, c0, c0), Color.argb((int)a1, c1, c1, c1), Shader.TileMode.MIRROR);
    }
    private LinearGradient getGradient(float x0, float y0, float x1, float y1, float a, int c0, int c1) {
        int aa = (int)a;
        return new LinearGradient(x0, y0, x1, y1, Color.argb(aa, c0, c0, c0), Color.argb(aa, c1, c1, c1), Shader.TileMode.MIRROR);
    }

    private Paint p1 = new Paint();
    private void drawRect(Canvas c, LinearGradient gr, float x0, float y0, float x1, float y1) {
        if (gr != null) {
            p1.setShader(gr);
            c.drawRect(x0, y0, x1, y1, p1);
        }
    }
    private void drawRect(Canvas c, float x, float y, float w, float h) {
        path.reset();
        path.moveTo(x, y);
        path.lineTo(x + w, y + h / 2);
        path.lineTo(x, y + h);
        path.lineTo(x, y);
        c.drawPath(path, p1);
    }
    //endregion

    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);

        if (isVisible()) {
            float dw = .2f * w;
            float dw25 = .025f * w;
            float x0 = x - dw;
            float x1 = x + w + dw;
            float y1 = y + h;
            float y3 = y + h / 3;

            drawRect(c, gradientWave, 0, y, MetrixUtils.screen_metrix_width * waveProgress, y1);
            if (isCurrentUser) {
                drawHighlight(c);
                drawRect(c, gradient0, x0, y, x1, y1);
                drawRect(c, gradient1, x0, y, x1, y3);
                //c.drawRect(x0, y, x1, y1, paintCurrent0); // контур
                c.drawLine(x0, y, x1, y, paintCurrent0);
                txtName.isPaintShadow = true;
                drawRect(c, x, y, dw25, h);
                drawRect(c, x + w, y, -dw25, h);
            } else {
                drawRect(c, gradient0, x0, y, x1, y1);
                c.drawLine(x0, y, x1, y, paint0);
                txtName.isPaintShadow = true;
            }
            drawRect(c, gradientTouched, x0, y, x1, y1);

            star.drawFrame(c);
            potion.drawFrame(c);
            txtIndex.drawFrame(c);
            txtScore.drawFrame(c);
            txtName.setMaxWidth(txtScore.x - txtName.x - txtScore.getTextWidth() - .01f * MetrixUtils.screen_metrix_width);
            txtName.drawFrame(c);
            crown.drawFrame(c);
            glares.drawFrame(c);
            orden.drawFrame(c);
        }
    }

    /*private LinearGradient gradient;
    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);

        if (isVisible()) {

            float dw = .2f * w;
            float dw25 = .025f * w;
            float x0 = x - dw;
            float x1 = x + w + dw;
            float y1 = y + h;
            float y3 = y + h / 3;
            float aa = alpha / 255f;

            //region wave progress
            gradient = new LinearGradient(0, y, MetrixUtils.screen_metrix_width * waveProgress, y,
                    Color.argb((int) (0 * aa), 255, 255, 255), Color.argb((int) (96 * aa), 255, 255, 255), Shader.TileMode.MIRROR);
            p1.setShader(gradientWave);
            c.drawRect(0, y, MetrixUtils.screen_metrix_width * waveProgress, y1, p1);
            //endregion

            //if (signsCounter != null)
            //    signsCounter.drawFrame(c);

            //region background
            if (isCurrentUser) {

                drawHighlight(c);

                int aaa = (int) (50 * aa);
                gradient = new LinearGradient(x0, y, x1, y1, Color.argb(aaa, 160, 160, 160), Color.argb(aaa, 255, 255, 255), Shader.TileMode.MIRROR);
                p1.setShader(gradient0);
                c.drawRect(x0, y, x1, y1, p1);

                gradient = new LinearGradient(x0, y, x1, y3, Color.argb((int) (40 * aa), 255, 255, 255), Color.argb((int) (20 * aa), 255, 255, 255), Shader.TileMode.MIRROR);
                p1.setShader(gradient1);
                c.drawRect(x0, y, x1, y3, p1);
                //c.drawRect(x0, y, x1, y1, paintCurrent0); // контур
                c.drawLine(x0, y, x1, y, paintCurrent0);
                txtName.isPaintShadow = true;

                drawRect(c, x, y, dw25, h);
                drawRect(c, x + w, y, -dw25, h);

            } else {

                int aaa = (int) (150 * aa);
                gradient = new LinearGradient(x0, y, x0, y1, Color.argb(aaa, 64, 64, 64), Color.argb(aaa, 40, 40, 40), Shader.TileMode.MIRROR);
                p1.setShader(gradient0);
                c.drawRect(x0, y, x1, y1, p1);
                //p1 = new Paint();
                //gradient = new LinearGradient(x0, y, x1, y3, Color.argb((int)(40 * aa) , 255, 255, 255), Color.argb((int)(20 * aa), 255, 255, 255), Shader.TileMode.MIRROR);
                //p1.setShader(gradient);
                //c.drawRect(new RectF(x0, y, x1, y3), p1);
                ////c.drawRect(x0, y, x1, y1, paint0); // контур
                //c.drawLine(x0, y, x1, y, paint0);
                txtName.isPaintShadow = true;
            }
            //if (waveProgress >= 1) {
            //    float ww = w / 60;
            //    float y8 = y + .9f * h;
            //    p.reset();
            //    p.moveTo(x - ww, y);
            //    p.lineTo(x - ww, y8);
            //    p.lineTo(x, y8 - ww);
            //    p.lineTo(x + ww, y8);
            //    p.lineTo(x + ww, y);
            //    p1 = new Paint();
            //    int aaa = (int) (100 * aa);
            //    gradient = new LinearGradient(x, y, x, y8, Color.argb(aaa, 0, 150, 0), Color.argb(aaa, 50, 200, 30), Shader.TileMode.MIRROR);
            //    p1.setShader(gradient);
            //    c.drawPath(p, p1);
            //}

            //if (waveProgress >= 1)
            //    back.drawFrame(c);
            //endregion

            // region touched
            int aaa = (int) (currentTouchedAlpha * aa);
            gradient = new LinearGradient(x0, y, x1, y1, Color.argb(aaa, 160, 160, 160), Color.argb(aaa, 255, 255, 255), Shader.TileMode.MIRROR);
            p1.setShader(gradientTouched);
            c.drawRect(x0, y, x1, y1, p1);
            //endregion

            star.drawFrame(c);
            txtIndex.drawFrame(c);
            txtScore.drawFrame(c);

            txtName.setMaxWidth(txtScore.x - txtName.x - txtScore.getTextWidth() - .01f * MetrixUtils.screen_metrix_width);
            txtName.drawFrame(c);

            crown.drawFrame(c);
            glares.drawFrame(c);
            orden.drawFrame(c);
        }
    }*/

    @Override
    public void recycle() {
        super.recycle();

        if (txtIndex != null)
            txtIndex.recycle();
        if (txtName != null)
            txtName.recycle();
        if (txtScore != null)
            txtScore.recycle();
        if (potion != null)
            potion.recycle();
        //if (back != null)
        //   back.recycle();
        //if (signsCounter != null)
        //    signsCounter.recycle();
        if (star != null)
            star.recycle();
        if (crown != null)
            crown.recycle();
        if (glares != null)
            glares.recycle();
        if (orden != null)
            orden.recycle();
        if (path != null)
            path.reset();

        txtIndex = null;
        txtName = null;
        txtScore = null;
        potion = null;
        //back = null;
        star = null;
        //signsCounter = null;
        crown = null;
        glares = null;
        orden = null;

        paint1 = null;
        paint0 = null;
        paintCurrent0 = null;
        //p1 = null;
        path = null;
    }
}
