/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/20/20 12:18 PM
 * Last modified 3/20/20 11:13 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameView.Utils

sealed class GestureConstants {
    class SwipeHorizontal(var horizontalDirection: Int) : GestureConstants()
    class SwipeVertical(var verticallDirection: Int) : GestureConstants()
}

class GestureListenerConstants {

    companion object {
        const val SWIPE_UP = 1
        const val SWIPE_DOWN = 2
        const val SWIPE_LEFT = 3
        const val SWIPE_RIGHT = 4

        const val MODE_SOLID = 1
        const val MODE_DYNAMIC = 2

        const val ACTION_FAKE = -666
    }
}