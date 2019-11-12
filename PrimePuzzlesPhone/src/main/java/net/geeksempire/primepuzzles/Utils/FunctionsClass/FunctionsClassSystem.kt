/*
 * Copyright (c) 2019.  All Rights Reserved for Geeks Empire.
 * Created by Elias Fazel on 11/11/19 6:09 PM
 * Last modified 11/11/19 6:08 PM
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

    @Throws(Exception::class)
    fun networkConnection(): Boolean {
        var networkAvailable = false

        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network: Network = connectivityManager.activeNetwork!!
        val networkCapabilities: NetworkCapabilities = connectivityManager.getNetworkCapabilities(network)!!

        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            networkAvailable = true
        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            networkAvailable = true
        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
            networkAvailable = true
        }

        return networkAvailable
    }
}