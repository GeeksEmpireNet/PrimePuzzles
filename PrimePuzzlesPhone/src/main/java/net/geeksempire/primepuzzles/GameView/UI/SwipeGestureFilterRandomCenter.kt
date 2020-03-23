/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/23/20 2:35 PM
 * Last modified 3/23/20 2:04 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameView.UI

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import net.geeksempire.primepuzzles.GameView.Utils.GestureConstants
import net.geeksempire.primepuzzles.GameView.Utils.GestureListenerConstants
import net.geeksempire.primepuzzles.GameView.Utils.GestureListenerInterface
import kotlin.math.abs

class SwipeGestureFilterRandomCenter(private val context: Context,
                                     private val gesturedRandomCenterView: AppCompatButton,
                                     private val gestureListenerInterface: GestureListenerInterface) : SimpleOnGestureListener() {


    private val gestureDetector: GestureDetector = GestureDetector(context, this@SwipeGestureFilterRandomCenter)

    private var tapIndicator = false


    private var swipeMinDistance: Int  = 100
    private var swipeMaxDistance: Int  = 1000

    private var swipeMinVelocity: Int  = 10

    private var mode: Int = GestureListenerConstants.MODE_DYNAMIC
    private var swipeMode: Int = 0


    override fun onFling(downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float) : Boolean {

        val xDistance = abs(downMotionEvent.x - moveMotionEvent.x)
        val yDistance = abs(downMotionEvent.y - moveMotionEvent.y)

        var motionEventConsumed = false

        if (abs(initVelocityY) >= this.swipeMinVelocity && yDistance > this.swipeMinDistance && xDistance < yDistance) {//Vertical

            swipeMode = if (downMotionEvent.y > moveMotionEvent.y) {//Down -> Up
                gestureListenerInterface.onSwipeGesture(
                    GestureConstants.SwipeVertical(
                        GestureListenerConstants.SWIPE_UP), downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)
                GestureListenerConstants.SWIPE_UP

            } else {//Up -> Down
                gestureListenerInterface.onSwipeGesture(
                    GestureConstants.SwipeVertical(
                        GestureListenerConstants.SWIPE_DOWN), downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)
                GestureListenerConstants.SWIPE_DOWN

            }

            motionEventConsumed = true
        }

        if (abs(initVelocityX) >= this.swipeMinVelocity && xDistance > this.swipeMinDistance && yDistance < xDistance) {//Horizontal

            swipeMode = if (downMotionEvent.x > moveMotionEvent.x) {//Right -> Left
                gestureListenerInterface.onSwipeGesture(
                    GestureConstants.SwipeHorizontal(
                        GestureListenerConstants.SWIPE_LEFT), downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)
                GestureListenerConstants.SWIPE_LEFT

            } else {//Left -> Right
                gestureListenerInterface.onSwipeGesture(
                    GestureConstants.SwipeHorizontal(
                        GestureListenerConstants.SWIPE_RIGHT), downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)
                GestureListenerConstants.SWIPE_RIGHT

            }

            motionEventConsumed = true
        }

        return motionEventConsumed
    }

    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {

        return false
    }

    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        gestureListenerInterface.onSingleTapUp(motionEvent)

        return false
    }

    override fun onLongPress(motionEvent: MotionEvent) {

        gestureListenerInterface.onLongPress(motionEvent)
    }

    fun onTouchEvent(motionEvent: MotionEvent) {
        val motionEventConsumed = this@SwipeGestureFilterRandomCenter.gestureDetector.onTouchEvent(motionEvent)

        if (this@SwipeGestureFilterRandomCenter.mode == GestureListenerConstants.MODE_SOLID) {

            motionEvent.action = MotionEvent.ACTION_CANCEL

        } else if (this@SwipeGestureFilterRandomCenter.mode == GestureListenerConstants.MODE_DYNAMIC) {

            if (motionEvent.action == GestureListenerConstants.ACTION_FAKE) {

                motionEvent.action = MotionEvent.ACTION_UP

            } else if (motionEventConsumed) {

                motionEvent.action = MotionEvent.ACTION_CANCEL

            } else if (this@SwipeGestureFilterRandomCenter.tapIndicator) {

                motionEvent.action = MotionEvent.ACTION_DOWN

                this@SwipeGestureFilterRandomCenter.tapIndicator = false
            }
        }
    }

    private fun swipeMode() : Int {

        return this@SwipeGestureFilterRandomCenter.swipeMode
    }
}
