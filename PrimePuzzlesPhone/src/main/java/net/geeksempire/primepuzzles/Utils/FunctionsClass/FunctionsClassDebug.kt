/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/17/20 11:24 AM
 * Last modified 3/17/20 11:19 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.Utils.FunctionsClass

import net.geeksempire.primepuzzles.BuildConfig

class FunctionsClassDebug {

    companion object {
        fun PrintDebug(debugMessage: Any?) {
            if (BuildConfig.DEBUG) {
                println("*** ${debugMessage} ***")
            }
        }
    }
}