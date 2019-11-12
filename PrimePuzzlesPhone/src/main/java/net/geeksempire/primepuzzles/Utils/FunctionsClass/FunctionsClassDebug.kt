/*
 * Copyright (c) 2019.  All Rights Reserved for Geeks Empire.
 * Created by Elias Fazel on 11/11/19 6:09 PM
 * Last modified 11/11/19 6:08 PM
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import android.app.Activity
import android.content.Context
import net.geeksempire.primepuzzles.BuildConfig

class FunctionsClassDebug {

    lateinit var activity: Activity
    lateinit var context: Context

    constructor(activity: Activity, context: Context) {
        this.activity = activity
        this.context = context
    }

    constructor(context: Context) {
        this.context = context
    }

    init {

    }

    companion object {
        fun PrintDebug(debugMessage: Any?) {
            if (BuildConfig.DEBUG) {
                println("*** ${debugMessage} ***")
            }
        }

        const val A_USER_WINS: String = "A_USER_WINS"
    }
}