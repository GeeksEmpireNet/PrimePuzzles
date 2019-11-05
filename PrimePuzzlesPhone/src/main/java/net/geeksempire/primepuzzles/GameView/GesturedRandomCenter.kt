package net.geeksempire.primepuzzles.GameView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import net.geeksempire.primepuzzles.GameLogic.GameVariables
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug
import net.geeksempire.primepuzzles.Utils.UI.SwipeGestureFilterRandomCenter

class GesturedRandomCenter : Button,
    SwipeGestureFilterRandomCenter.GestureListener {

    var swipeGestureFilterRandomCenter: SwipeGestureFilterRandomCenter

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        swipeGestureFilterRandomCenter = SwipeGestureFilterRandomCenter(this@GesturedRandomCenter, context, this@GesturedRandomCenter)

    }

    constructor(context: Context) : super(context) {
        swipeGestureFilterRandomCenter = SwipeGestureFilterRandomCenter(this@GesturedRandomCenter, context, this@GesturedRandomCenter)

    }

    init {
        val listTOfRandom = ArrayList<Int>()
        listTOfRandom.addAll(2..9)

        val randomCenterValue: Int = listTOfRandom.random()
        this@GesturedRandomCenter.text = "${randomCenterValue}"
        GameVariables.CENTER_VALUE.value = randomCenterValue
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun dispatchTouchEvent(motionEvent: MotionEvent): Boolean {
        this.swipeGestureFilterRandomCenter.onTouchEvent(motionEvent)

        return super.dispatchTouchEvent(motionEvent)
    }

    override fun onSwipe(direction: Int) {
        when (direction) {
            SwipeGestureFilterRandomCenter.SWIPE_DOWN -> {
                FunctionsClassDebug.PrintDebug("SWIPE_DOWN")

            }
            SwipeGestureFilterRandomCenter.SWIPE_LEFT -> {
                FunctionsClassDebug.PrintDebug("SWIPE_LEFT")


            }
            SwipeGestureFilterRandomCenter.SWIPE_RIGHT -> {
                FunctionsClassDebug.PrintDebug("SWIPE_RIGHT")


            }
            SwipeGestureFilterRandomCenter.SWIPE_UP -> {
                FunctionsClassDebug.PrintDebug("SWIPE_UP")

            }
        }
    }

    override fun onSingleTapUp() {
        FunctionsClassDebug.PrintDebug("SINGLE_TAP")

    }
}