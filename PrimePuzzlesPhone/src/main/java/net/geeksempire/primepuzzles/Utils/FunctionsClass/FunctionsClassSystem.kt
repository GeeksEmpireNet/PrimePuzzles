/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/22/20 4:29 PM
 * Last modified 3/22/20 4:04 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import net.geeksempire.primepuzzles.BuildConfig

class FunctionsClassSystem(private val context: Context) {

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

    @Throws(Exception::class)
    fun networkConnection() : Boolean {

        val connectivityManager: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        return if (connectivityManager != null) {
            val network: Network? = connectivityManager.activeNetwork
            val networkCapabilities: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(network)

            if (BuildConfig.DEBUG) {
              false
            } else if (networkCapabilities != null) {

                when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        true
                    }
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        true
                    }
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                        true
                    }
                    else -> {
                        false
                    }
                }
            } else {
                false
            }
        } else {
            false
        }
    }
}