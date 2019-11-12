/*
 * Copyright © 2019 By Geeks Empire.
 *
 * Created by Elias Fazel on 11/11/19 6:49 PM
 * Last modified 11/11/19 6:48 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.loadingspin.sprite;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by ybq.
 */
public abstract class CircleLayoutContainer extends SpriteContainer {

    @Override
    public void drawChild(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            int count = canvas.save();
            canvas.rotate(i * 360 / getChildCount(),
                    getBounds().centerX(),
                    getBounds().centerY());
            sprite.draw(canvas);
            canvas.restoreToCount(count);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        bounds = clipSquare(bounds);
        int radius = (int) (bounds.width() * Math.PI / 3.6f / getChildCount());
        int left = bounds.centerX() - radius;
        int right = bounds.centerX() + radius;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            sprite.setDrawBounds(left, bounds.top, right, bounds.top + radius * 2);
        }
    }
}
