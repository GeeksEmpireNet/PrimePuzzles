package net.geeksempire.primepuzzles.GameView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.material.button.MaterialButton
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug
import net.geeksempire.primepuzzles.Utils.UI.SwipeGestureFilter

class GestureRandomCenter : MaterialButton,
    SwipeGestureFilter.GestureListener {

    var swipeGestureFilter: SwipeGestureFilter

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        swipeGestureFilter = SwipeGestureFilter(this@GestureRandomCenter, context, this@GestureRandomCenter)

    }

    constructor(context: Context) : super(context) {
        swipeGestureFilter = SwipeGestureFilter(this@GestureRandomCenter, context, this@GestureRandomCenter)

    }

    init {

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun dispatchTouchEvent(motionEvent: MotionEvent): Boolean {
        this.swipeGestureFilter.onTouchEvent(motionEvent)

        return super.dispatchTouchEvent(motionEvent)
    }

    override fun onSwipe(direction: Int) {
        when (direction) {
            SwipeGestureFilter.SWIPE_DOWN -> {
                FunctionsClassDebug.PrintDebug("SWIPE_DOWN")


            }
            SwipeGestureFilter.SWIPE_LEFT -> {
                FunctionsClassDebug.PrintDebug("SWIPE_LEFT")


            }
            SwipeGestureFilter.SWIPE_RIGHT -> {
                FunctionsClassDebug.PrintDebug("SWIPE_RIGHT")


            }
            SwipeGestureFilter.SWIPE_UP -> {
                FunctionsClassDebug.PrintDebug("SWIPE_UP")


            }
        }
    }

    override fun onSingleTapUp() {
        FunctionsClassDebug.PrintDebug("SINGLE_TAP")

    }
}