/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/18/20 1:00 PM
 * Last modified 3/18/20 12:18 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import net.geeksempire.primepuzzles.GameData.GameVariablesViewModel
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameView.UI.SwipeGestureFilterRandomCenter
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug

class GesturedRandomCenter : AppCompatButton, GestureListenerInterface {

    private var swipeGestureFilterRandomCenter: SwipeGestureFilterRandomCenter

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        swipeGestureFilterRandomCenter =
            SwipeGestureFilterRandomCenter(
                context,
                this@GesturedRandomCenter,
                this@GesturedRandomCenter
            )

    }

    constructor(context: Context) : super(context) {
        swipeGestureFilterRandomCenter =
            SwipeGestureFilterRandomCenter(
                context,
                this@GesturedRandomCenter,
                this@GesturedRandomCenter
            )

    }

    init {
        val typeface = ResourcesCompat.getFont(context, R.font.play)
        this@GesturedRandomCenter.typeface = typeface

        val listTOfRandom = ArrayList<Int>()
        when (GameLevel().getGameDifficultyLevel()) {
            GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {
                listTOfRandom.addAll(2..9)
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {
                listTOfRandom.addAll(10..99)
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_THREE_DIGIT-> {
                listTOfRandom.addAll(100..999)
            }
            GameLevel.GAME_DIFFICULTY_LEVEL_FOUR_DIGIT-> {
                listTOfRandom.addAll(1000..9999)
            }
        }

        val randomCenterValue: Int = listTOfRandom.random()
        this@GesturedRandomCenter.text = "${randomCenterValue}"
        GameVariablesViewModel.CENTER_VALUE.value = randomCenterValue
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun dispatchTouchEvent(motionEvent: MotionEvent) : Boolean {
        this.swipeGestureFilterRandomCenter.onTouchEvent(motionEvent)

        return super.dispatchTouchEvent(motionEvent)
    }

    override fun onSwipe (
        direction: Int,
        downMotionEvent: MotionEvent,
        moveMotionEvent: MotionEvent,
        initVelocityX: Float,
        initVelocityY: Float) {

        when (direction) {
            GestureListenerConstants.SWIPE_DOWN -> {
                FunctionsClassDebug.PrintDebug("SWIPE_DOWN")

            }
            GestureListenerConstants.SWIPE_LEFT -> {
                FunctionsClassDebug.PrintDebug("SWIPE_LEFT")


            }
            GestureListenerConstants.SWIPE_RIGHT -> {
                FunctionsClassDebug.PrintDebug("SWIPE_RIGHT")


            }
            GestureListenerConstants.SWIPE_UP -> {
                FunctionsClassDebug.PrintDebug("SWIPE_UP")

            }
        }
    }

    override fun onSingleTapUp(motionEvent: MotionEvent) {
        FunctionsClassDebug.PrintDebug("SINGLE_TAP")
    }

    override fun onLongPress(motionEvent: MotionEvent) {
        FunctionsClassDebug.PrintDebug("LONG_PRESS")
    }
}