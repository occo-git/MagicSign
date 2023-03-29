package com.softigress.magicsigns.UI._base.Controls._base.Texts;

import android.graphics.Canvas;

import com.softigress.magicsigns.R;
import com.softigress.magicsigns._Base._Drawing._base.Alignment.DrawingHAlign;
import com.softigress.magicsigns._Base._Drawing._base.DrawingBase;
import com.softigress.magicsigns._system.Utils.MetrixUtils;

public class MessageText extends DrawingText {

    public boolean isIdea = false;
    private DrawingBase idea;

    public MessageText(DrawingHAlign halign) {
        super(halign);
        idea = new DrawingBase(.068f, R.string.bmp_item_idea);
    }

    @Override
    public void drawFrame(Canvas c) {
        super.drawFrame(c);

        if (isVisible && idea != null && isIdea) {
            idea.setAlpha(alpha);
            if (rect != null) {
                idea.setPoint(
                        rect.left / MetrixUtils.screen_metrix_width - idea.fd / 2,
                        rect.top / MetrixUtils.screen_metrix_height);
                idea.drawFrame(c);
            }
        }
    }

    public void recycle() {
        super.recycle();

        if (idea != null)
            idea.recycle();
        idea = null;
    }
}
