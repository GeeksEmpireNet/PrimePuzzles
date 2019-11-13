/*
 * Copyright © 2019 By Geeks Empire.
 *
 * Created by Elias Fazel on 11/13/19 2:52 PM
 * Last modified 10/12/19 7:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.ProgressBar;

import android.content.Context;

/**
 * Created by moos on 2018/3/16.
 * utils to help develop
 */

public class Utils {
    /**
     * change dp to px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * change sp to px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
