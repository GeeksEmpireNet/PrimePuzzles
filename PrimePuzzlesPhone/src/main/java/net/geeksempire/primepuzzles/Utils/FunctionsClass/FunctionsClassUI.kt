package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import android.util.DisplayMetrics

class FunctionsClassUI(initContext: Context) {

    private val context: Context = initContext

    fun DpToPixel(dp: Float): Float {
        val displayMetrics = context.resources.displayMetrics
        return dp * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}