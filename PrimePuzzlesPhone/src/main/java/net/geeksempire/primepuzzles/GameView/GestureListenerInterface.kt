/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/18/20 5:23 PM
 * Last modified 3/18/20 5:13 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameView

import android.view.MotionEvent

interface GestureListenerInterface {
    fun onSwipeGesture(gestureDirection: GestureConstants, downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float) {}

    fun onSingleTapUp(motionEvent: MotionEvent) {}
    fun onLongPress(motionEvent: MotionEvent) {}
}