/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/18/20 1:00 PM
 * Last modified 3/18/20 12:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameView

import android.view.MotionEvent

interface GestureListenerInterface {
    fun onSwipe(direction: Int, downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float)

    fun onSingleTapUp(motionEvent: MotionEvent)
    fun onLongPress(motionEvent: MotionEvent)
}