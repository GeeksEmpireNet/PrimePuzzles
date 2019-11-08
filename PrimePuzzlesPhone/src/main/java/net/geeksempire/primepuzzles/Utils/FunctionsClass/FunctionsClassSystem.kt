package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator


class FunctionsClassSystem(initContext: Context) {

    var context: Context = initContext

    fun doVibrate() {
        val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(123, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(159)
        }
    }

    fun getStatusBarHeight(context: Context) : Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    fun getNavigationBarHeight(context: Context) : Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }
}