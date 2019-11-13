/*
 * Copyright © 2019 By Geeks Empire.
 *
 * Created by Elias Fazel on 11/12/19 5:40 PM
 * Last modified 11/12/19 5:33 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameView.UI

import android.content.Context
import android.os.Handler
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.Button
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import net.geeksempire.primepuzzles.GameInformation.GameInformationVariable
import net.geeksempire.primepuzzles.GameInformation.GameVariables
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameLogic.GameOperations
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.*
import kotlin.math.abs

class SwipeGestureFilterRandomCenter(private val view: Button, initContext: Context, private val gestureListener: GestureListener) : SimpleOnGestureListener() {

    private val context: Context = initContext

    private val functionsClassSystem: FunctionsClassSystem = FunctionsClassSystem(initContext)
    private val functionsClassUI: FunctionsClassUI = FunctionsClassUI(initContext)
    private val functionsClassGame: FunctionsClassGame = FunctionsClassGame(initContext)
    private val functionsClassMath: FunctionsClassMath = FunctionsClassMath(initContext)

    private val gameOperations: GameOperations = GameOperations(initContext)

    private var tapIndicator = false
    private val gestureDetector: GestureDetector = GestureDetector(initContext, this@SwipeGestureFilterRandomCenter)

    private var swipeMinDistance: Int  = 10
    private var swipeMaxDistance: Int  = 1000

    private var swipeMinVelocity: Int  = 10

    private var mode: Int =
        MODE_DYNAMIC
    private var swipeMode: Int = 0
    private var running: Boolean = true


    private var triggerCenterRandomChange: Boolean = false
    private var divisibleTriggered: Boolean = false

    init {

    }

    interface GestureListener {
        fun onSwipe(direction: Int)

        fun onSingleTapUp()
        fun onLongPress()
    }

    override fun onFling(downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float): Boolean {

        val velocityX: Float = initVelocityX
        val velocityY: Float = initVelocityY

        val xDistance = abs(downMotionEvent.x - moveMotionEvent.x)
        val yDistance = abs(downMotionEvent.y - moveMotionEvent.y)

        if (xDistance > this.swipeMaxDistance || yDistance > this.swipeMaxDistance) {
            return false
        }

        val springForce: SpringForce by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SpringForce(0f).apply {
                stiffness = SpringForce.STIFFNESS_LOW
                dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
            }
        }

        val springAnimationTranslationX: SpringAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SpringAnimation(view, DynamicAnimation.TRANSLATION_X).setSpring(springForce)
        }
        val springAnimationTranslationY: SpringAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SpringAnimation(view, DynamicAnimation.TRANSLATION_Y).setSpring(springForce)
        }

        val flingAnimationX: FlingAnimation by lazy(LazyThreadSafetyMode.NONE) {
            FlingAnimation(view, DynamicAnimation.X)
                .setFriction(1.3f)
                .setMinValue(0f)
                .setMaxValue(context.resources.displayMetrics.widthPixels.toFloat() - (view.width))
        }

        val flingAnimationY: FlingAnimation by lazy(LazyThreadSafetyMode.NONE) {
            FlingAnimation(view, DynamicAnimation.Y)
                .setFriction(1.3f)
                .setMinValue(0f)
                .setMaxValue(context.resources.displayMetrics.widthPixels.toFloat() - (view.width))
        }

        flingAnimationX.addEndListener { animation, canceled, value, velocity ->

            when (swipeMode()) {
                SWIPE_LEFT -> {

                    if (gameOperations.determineLeftValue()) {
                        //CORRECT ANSWER
                        FunctionsClassDebug.PrintDebug("Divisible Triggered ${divisibleTriggered}")

                        divisibleTriggered = true

                        if (divisibleTriggered) {
                            GameVariables.TOGGLE_SNACKBAR.value = false

                            FunctionsClassDebug.PrintDebug("Dismissing Hint")
                        }

                        GameVariables.POSITIVE_POINT.value = 3
                        GameVariables.DIVISIBLE_POSITIVE_POINT.value = 3
                    } else {
                        //WRONG ANSWER
                        FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                        functionsClassGame.playWrongSound()

                        GameVariables.NEGATIVE_POINT.value = 3
                        GameVariables.DIVISIBLE_NEGATIVE_POINT.value = 3
                    }
                }
                SWIPE_RIGHT -> {

                    if (gameOperations.determineRightValue()) {
                        //CORRECT ANSWER
                        FunctionsClassDebug.PrintDebug("Divisible Triggered ${divisibleTriggered}")

                        divisibleTriggered = true

                        if (divisibleTriggered) {
                            GameVariables.TOGGLE_SNACKBAR.value = false

                            FunctionsClassDebug.PrintDebug("Dismissing Hint")
                        }

                        GameVariables.POSITIVE_POINT.value = 3
                        GameVariables.DIVISIBLE_POSITIVE_POINT.value = 3
                    } else {
                        //WRONG ANSWER
                        FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                        functionsClassGame.playWrongSound()

                        GameVariables.NEGATIVE_POINT.value = 3
                        GameVariables.DIVISIBLE_NEGATIVE_POINT.value = 3
                    }
                }
            }

            Handler().postDelayed({
                    springAnimationTranslationX.start()
                    springAnimationTranslationY.start()

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
                    view.text = "${randomCenterValue}"
                    GameVariables.CENTER_VALUE.value = randomCenterValue

                    triggerCenterRandomChange = true
                    divisibleTriggered = false
                }, 333)

            GameVariables.SHUFFLE_PROCESS_POSITION.value = GameVariables.SHUFFLE_PROCESS_POSITION.value!! + 1
            GameVariables.SHUFFLE_PROCESS_VALUE.value = GameVariables.SHUFFLE_PROCESS_VALUE.value!! + 1
        }

        flingAnimationY.addEndListener { animation, canceled, value, velocity ->

            when (swipeMode()) {
                SWIPE_UP -> {

                    if (gameOperations.determineTopValue()) {
                        divisibleTriggered = true

                        if (divisibleTriggered) {
                            GameVariables.TOGGLE_SNACKBAR.value = false

                            FunctionsClassDebug.PrintDebug("Dismissing Hint")
                        }

                        //CORRECT ANSWER
                        FunctionsClassDebug.PrintDebug("Divisible Triggered ${divisibleTriggered}")

                        GameVariables.POSITIVE_POINT.value = 3
                        GameVariables.DIVISIBLE_POSITIVE_POINT.value = 3
                    } else {
                        //WRONG ANSWER
                        FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                        functionsClassGame.playWrongSound()

                        GameVariables.NEGATIVE_POINT.value = 3
                        GameVariables.DIVISIBLE_NEGATIVE_POINT.value = 3
                    }
                }
                SWIPE_DOWN -> {

                    if (gameOperations.determinePrimeValue()) {
                        //CORRECT ANSWER
                        FunctionsClassDebug.PrintDebug("${GameVariables.CENTER_VALUE.value} IS A PRIME Number")

                        GameVariables.POSITIVE_POINT.value = 13
                        GameVariables.PRIME_POSITIVE_POINT.value = 13


                        GameVariables.PRIME_NUMBER_DETECTED.value = true

                        GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT = context.getString(R.string.primeDetect)
                        GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT= context.getString(R.string.primeDetectAction)

                        GameVariables.TOGGLE_SNACKBAR.value = true
                        GameInformationVariable.snackBarAction = GameInformationVariable.PRIME_NUMBER_ACTION
                    } else {
                        //WRONG ANSWER
                        FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                        functionsClassGame.playWrongSound()

                        GameVariables.NEGATIVE_POINT.value = 13
                        GameVariables.PRIME_NEGATIVE_POINT.value = 13
                    }
                }
            }

            Handler()
                .postDelayed({
                    springAnimationTranslationX.start()
                    springAnimationTranslationY.start()

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
                    view.text = "${randomCenterValue}"
                    GameVariables.CENTER_VALUE.value = randomCenterValue

                    triggerCenterRandomChange = true
                    divisibleTriggered = false
                }, 333)

            GameVariables.SHUFFLE_PROCESS_POSITION.value = GameVariables.SHUFFLE_PROCESS_POSITION.value!! + 1
            GameVariables.SHUFFLE_PROCESS_VALUE.value = GameVariables.SHUFFLE_PROCESS_VALUE.value!! + 1
        }

        var result = false
        if (abs(velocityY) >= this.swipeMinVelocity && yDistance > this.swipeMinDistance && xDistance < yDistance) {//Vertical
            if (downMotionEvent.y > moveMotionEvent.y) {//Bottom -> Up
                this.gestureListener.onSwipe(SWIPE_UP)
                swipeMode =
                    SWIPE_UP


            } else {//Up -> Bottom
                this.gestureListener.onSwipe(SWIPE_DOWN)
                swipeMode =
                    SWIPE_DOWN


            }

            flingAnimationY.setStartValue(view.y)
            flingAnimationY.setStartVelocity(velocityY)
            flingAnimationY.start()

            result = true
        }

        if (abs(velocityX) >= this.swipeMinVelocity && xDistance > this.swipeMinDistance && yDistance < xDistance) {//Horizontal
            if (downMotionEvent.x > moveMotionEvent.x) {//Right -> Left
                this.gestureListener.onSwipe(SWIPE_LEFT)
                swipeMode =
                    SWIPE_LEFT


            } else {//Left -> Right
                this.gestureListener.onSwipe(SWIPE_RIGHT)
                swipeMode =
                    SWIPE_RIGHT


            }

            flingAnimationX.setStartValue(view.x)
            flingAnimationX.setStartVelocity(velocityX)
            flingAnimationX.start()

            result = true
        }
        return result
    }

    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {

        return false
    }

    override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
        this.gestureListener.onSingleTapUp()

        return false
    }

    override fun onLongPress(motionEvent: MotionEvent) {
        this.gestureListener.onLongPress()

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

        if (!functionsClassMath.isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.TOP_VALUE.value!!)
            && !functionsClassMath.isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.LEFT_VALUE.value!!)
            && !functionsClassMath.isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.RIGHT_VALUE.value!!)
            && !functionsClassMath.isNumberPrime(GameVariables.CENTER_VALUE.value!!)) {

            val randomCenterValue: Int = listTOfRandom.random()
            view.text = "${randomCenterValue}"
            GameVariables.CENTER_VALUE.value = randomCenterValue

            //CORRECT ANSWER
            triggerCenterRandomChange = true

            functionsClassGame.playChangedCenterRandomSound()

            GameVariables.POSITIVE_POINT.value = 3
            GameVariables.CHANGE_CENTER_RANDOM_POSITIVE_POINT.value = 3
        } else {
            GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT = context.getString(R.string.thinkMore)
            GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT= context.getString(R.string.showHint)

            GameVariables.TOGGLE_SNACKBAR.value = true

            //WRONG ANSWER
            functionsClassGame.playWrongSound()

            GameVariables.NEGATIVE_POINT.value = 3
            GameVariables.CHANGE_CENTER_RANDOM_NEGATIVE_POINT.value = 3
        }

        functionsClassSystem.doVibrate()
    }

    fun onTouchEvent(motionEvent: MotionEvent) {
        if (!this.running) {
            return
        }

        val result = this.gestureDetector.onTouchEvent(motionEvent)
        if (this.mode == MODE_SOLID) {
            motionEvent.action = MotionEvent.ACTION_CANCEL
        } else if (this.mode == MODE_DYNAMIC) {
            if (motionEvent.action == ACTION_FAKE) {
                motionEvent.action = MotionEvent.ACTION_UP
            } else if (result) {
                motionEvent.action = MotionEvent.ACTION_CANCEL
            } else if (this.tapIndicator) {
                motionEvent.action = MotionEvent.ACTION_DOWN

                this.tapIndicator = false
            }
        }
        //Else -> Just Do Nothing | MODE_TRANSPARENT
    }

    fun setEnabled(status: Boolean) {
        this.running = status
    }

    private fun swipeMode(): Int {

        return swipeMode
    }

    companion object {
        const val SWIPE_UP = 1
        const val SWIPE_DOWN = 2
        const val SWIPE_LEFT = 3
        const val SWIPE_RIGHT = 4

        const val MODE_TRANSPARENT = 0
        const val MODE_SOLID = 1
        const val MODE_DYNAMIC = 2

        private val ACTION_FAKE = -666
    }
}
