package com.softigress.magicsigns.UI._base.Controls._base.Progress;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Keep;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._Base._Drawing._interfaces.IDrawing;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;

// отображает прогресс прохождения Waves
public class CtrlWaveProgress implements IDrawing {

    private final float fx = .05f, fy = .125f, fw = .05f, fh = .55f;
    private float fh0;
    private float waveFy;
    private static final long showDuration = 3000;
    private static final long hideDuration = 3000;
    private static final long waveDuration = 3000;

    private DrawingBase wave;
    private Paint paint0, paint1;

    public CtrlWaveProgress() {
        wave = new DrawingBase(fw, R.string.bmp_wave);
        wave.setAlpha(0);
        setWaveFy(fy + fh);

        paint0 = PaintUtils.getPaintWhite(32);
        paint1 = PaintUtils.getPaintWhite(128);
    }

    @Keep
    private void setWaveFy(float ffy) {
        if (wave != null) {
            wave.setPoint(fx, ffy);
            waveFy = ffy;
        }
    }

    @Keep
    public void setAlpha(int a) {
        int a0 = (int)(32f * a / 255f);
        int a1 = (int)(128f * a / 255f);
        if (paint0 != null)
            paint0.setAlpha(a0);
        if (paint1 != null)
            paint1.setAlpha(a1);
        if (wave != null)
            wave.setAlpha(a);
    }

    //region wave
    private int count;
    public void nextWave() {
        count++;
        fh0 = fh * (float)(count - 1) / CurrentSettings.signWavesCount;
        startWaveAnim(fy + fh - fh0);
    }

    private Animator waveAnim = null;
    private void stopWaveAnim() {
        if (waveAnim != null && waveAnim.isStarted())
            waveAnim.cancel();
    }

    private void startWaveAnim(float nextFy) {
        stopWaveAnim();
        float prevFy = waveFy;
        waveAnim = ObjectAnimator.ofFloat(this, "waveFy", prevFy, nextFy).setDuration(waveDuration);
        waveAnim.start();
    }

    //endregion

    //region show / hide
    private Animator showhideAnim = null;
    private void stopShowHideAnim() {
        if (showhideAnim != null && showhideAnim.isStarted())
            showhideAnim.cancel();
    }

    public void show() {
        stopShowHideAnim();
        showhideAnim = ObjectAnimator.ofInt(this, "alpha", 0, 255).setDuration(showDuration);
        showhideAnim.start();
    }
    public void hide() {
        stopShowHideAnim();
        showhideAnim = ObjectAnimator.ofInt(this, "alpha", 255, 0).setDuration(hideDuration);
        showhideAnim.start();
    }
    //endregion

    //region IDrawing
    @Override
    public int getLayer() { return 0; }

    @Override
    public void calc() { }

    @Override
    public void drawFrame(Canvas c) {
        float x = fx * MetrixUtils.screen_metrix_width;
        float y = fy * MetrixUtils.screen_metrix_height;
        float r = fw * MetrixUtils.screen_metrix_width / 16f;
        float h = fh * MetrixUtils.screen_metrix_height;
        float deltaH = h / CurrentSettings.signWavesCount;
        float h0 = fh0 * MetrixUtils.screen_metrix_height;

        for (int i = 0; i < CurrentSettings.signWavesCount; i++) {
            float hh = i * deltaH;
            float rr = r + 2 * r * hh / h;
            c.drawCircle(x, y + h - hh, rr, hh < h0 ? paint1 : paint0);
        }

        if (wave != null)
            wave.drawFrame(c);
    }
    //endregion

    @Override
    public void recycle() {
        if (wave != null)
            wave.recycle();
        wave = null;
        paint0 = null;
        paint1 = null;
    }
}
