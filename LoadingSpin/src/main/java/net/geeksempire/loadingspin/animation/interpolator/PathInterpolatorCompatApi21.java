/*
 * Copyright (c) 2019.  All Rights Reserved for Geeks Empire.
 * Created by Elias Fazel on 11/11/19 6:09 PM
 * Last modified 11/11/19 6:08 PM
 */

package net.geeksempire.loadingspin.animation.interpolator;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/**
 * API 21+ implementation for path interpolator compatibility.
 */
class PathInterpolatorCompatApi21 {

    private PathInterpolatorCompatApi21() {
        // prevent instantiation
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Interpolator create(Path path) {
        return new PathInterpolator(path);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Interpolator create(float controlX, float controlY) {
        return new PathInterpolator(controlX, controlY);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Interpolator create(float controlX1, float controlY1,
                                      float controlX2, float controlY2) {
        return new PathInterpolator(controlX1, controlY1, controlX2, controlY2);
    }
}
