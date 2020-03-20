/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/20/20 12:18 PM
 * Last modified 3/20/20 12:18 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameView.Utils

import android.view.MotionEvent

interface GestureListenerInterface {
    fun onSwipeGesture(gestureDirection: GestureConstants, downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float) {}

    fun onSingleTapUp(motionEvent: MotionEvent) {}
    fun onLongPress(motionEvent: MotionEvent) {}
}