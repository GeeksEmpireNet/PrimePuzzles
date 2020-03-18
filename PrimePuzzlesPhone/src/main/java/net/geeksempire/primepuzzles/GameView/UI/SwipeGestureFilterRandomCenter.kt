/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/18/20 1:00 PM
 * Last modified 3/18/20 1:00 PM
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
import androidx.appcompat.widget.AppCompatButton
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import net.geeksempire.primepuzzles.GameData.GameInformationVariable
import net.geeksempire.primepuzzles.GameData.GameVariablesViewModel
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameLogic.GameOperations
import net.geeksempire.primepuzzles.GameView.GestureListenerConstants
import net.geeksempire.primepuzzles.GameView.GestureListenerInterface
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGame
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassMath
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassSystem
import kotlin.math.abs

class SwipeGestureFilterRandomCenter(private val context: Context,
                                     private val gesturedRandomCenterView: AppCompatButton,
                                     private val gestureListenerInterface: GestureListenerInterface) : SimpleOnGestureListener() {

    private val functionsClassSystem: FunctionsClassSystem = FunctionsClassSystem(context)
    private val functionsClassGame: FunctionsClassGame = FunctionsClassGame(context)
    private val functionsClassMath: FunctionsClassMath = FunctionsClassMath(context)

    private val gameOperations: GameOperations = GameOperations(context)

    private var tapIndicator = false
    private val gestureDetector: GestureDetector = GestureDetector(context, this@SwipeGestureFilterRandomCenter)

    private var swipeMinDistance: Int  = 10
    private var swipeMaxDistance: Int  = 1000

    private var swipeMinVelocity: Int  = 10

    private var mode: Int = GestureListenerConstants.MODE_DYNAMIC
    private var swipeMode: Int = 0


    private var triggerCenterRandomChange: Boolean = false
    private var divisibleTriggered: Boolean = false


    private val springForce: SpringForce by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_LOW
            dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
        }
    }

    private val springAnimationTranslationX: SpringAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        SpringAnimation(gesturedRandomCenterView, DynamicAnimation.TRANSLATION_X).setSpring(springForce)
    }
    private val springAnimationTranslationY: SpringAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        SpringAnimation(gesturedRandomCenterView, DynamicAnimation.TRANSLATION_Y).setSpring(springForce)
    }

    private val flingAnimationX: FlingAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        FlingAnimation(gesturedRandomCenterView, DynamicAnimation.X)
            .setFriction(1.3f)
            .setMinValue(0f)
            .setMaxValue(context.resources.displayMetrics.widthPixels.toFloat() - (gesturedRandomCenterView.width))
    }
    private val flingAnimationY: FlingAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        FlingAnimation(gesturedRandomCenterView, DynamicAnimation.Y)
            .setFriction(1.3f)
            .setMinValue(0f)
            .setMaxValue(context.resources.displayMetrics.widthPixels.toFloat() - (gesturedRandomCenterView.width))
    }

    override fun onFling(downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent, initVelocityX: Float, initVelocityY: Float) : Boolean {

        /*
        * Check to remove this checkpoint
        * */
        val xDistance = abs(downMotionEvent.x - moveMotionEvent.x)
        val yDistance = abs(downMotionEvent.y - moveMotionEvent.y)

        if (xDistance > this.swipeMaxDistance || yDistance > this.swipeMaxDistance) {
            return false
        }

        var motionEventConsumed = false
        if (abs(initVelocityY) >= this.swipeMinVelocity && yDistance > this.swipeMinDistance && xDistance < yDistance) {//Vertical

            swipeMode = if (downMotionEvent.y > moveMotionEvent.y) {//Bottom -> Up
                gestureListenerInterface.onSwipe(GestureListenerConstants.SWIPE_UP, downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)
                GestureListenerConstants.SWIPE_UP

            } else {//Up -> Bottom
                gestureListenerInterface.onSwipe(GestureListenerConstants.SWIPE_DOWN, downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)
                GestureListenerConstants.SWIPE_DOWN

            }

            flingAnimationY.setStartValue(gesturedRandomCenterView.y)
            flingAnimationY.setStartVelocity(initVelocityY)
            flingAnimationY.start()

            motionEventConsumed = true

            flingAnimationY.addEndListener { dynamicAnimation, canceled, value, velocity ->

                when (swipeMode()) {
                    GestureListenerConstants.SWIPE_UP -> {

                        if (gameOperations.determineTopValue()) {
                            divisibleTriggered = true

                            if (divisibleTriggered) {
                                GameVariablesViewModel.TOGGLE_SNACKBAR.postValue(false)

                                FunctionsClassDebug.PrintDebug("Dismissing Hint")
                            }

                            //CORRECT ANSWER
                            FunctionsClassDebug.PrintDebug("Divisible Triggered ${divisibleTriggered}")

                            GameVariablesViewModel.POSITIVE_POINT.postValue(3)
                            GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.postValue(3)
                        } else {
                            //WRONG ANSWER
                            FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                            functionsClassGame.playWrongSound()

                            GameVariablesViewModel.NEGATIVE_POINT.postValue(3)
                            GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.postValue(3)
                        }
                    }
                    GestureListenerConstants.SWIPE_DOWN -> {

                        if (gameOperations.determinePrimeValue()) {
                            //CORRECT ANSWER
                            FunctionsClassDebug.PrintDebug("${GameVariablesViewModel.CENTER_VALUE.value} IS A PRIME Number")

                            GameVariablesViewModel.POSITIVE_POINT.postValue(13)
                            GameVariablesViewModel.PRIME_POSITIVE_POINT.postValue(13)


                            GameVariablesViewModel.PRIME_NUMBER_DETECTED.postValue(true)

                            GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT = context.getString(R.string.primeDetect)
                            GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT= context.getString(R.string.primeDetectAction)

                            GameVariablesViewModel.TOGGLE_SNACKBAR.postValue(true)
                            GameInformationVariable.snackBarAction = GameInformationVariable.PRIME_NUMBER_ACTION
                        } else {
                            //WRONG ANSWER
                            FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                            functionsClassGame.playWrongSound()

                            GameVariablesViewModel.NEGATIVE_POINT.postValue(13)
                            GameVariablesViewModel.PRIME_NEGATIVE_POINT.postValue(13)
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
                    gesturedRandomCenterView.text = "${randomCenterValue}"
                    GameVariablesViewModel.CENTER_VALUE.postValue(randomCenterValue)

                    triggerCenterRandomChange = true
                    divisibleTriggered = false
                }, 333)

                GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.postValue(GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.value!! + 1)
                GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.postValue(GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.value!! + 1)
            }
        }

        if (abs(initVelocityX) >= this.swipeMinVelocity && xDistance > this.swipeMinDistance && yDistance < xDistance) {//Horizontal

            swipeMode = if (downMotionEvent.x > moveMotionEvent.x) {//Right -> Left
                gestureListenerInterface.onSwipe(GestureListenerConstants.SWIPE_LEFT, downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)
                GestureListenerConstants.SWIPE_LEFT

            } else {//Left -> Right
                gestureListenerInterface.onSwipe(GestureListenerConstants.SWIPE_RIGHT, downMotionEvent, moveMotionEvent, initVelocityX, initVelocityY)
                GestureListenerConstants.SWIPE_RIGHT

            }

            flingAnimationX.setStartValue(gesturedRandomCenterView.x)
            flingAnimationX.setStartVelocity(initVelocityX)
            flingAnimationX.start()

            motionEventConsumed = true

            flingAnimationX.addEndListener { dynamicAnimation, canceled, value, velocity ->

                when (swipeMode()) {
                    GestureListenerConstants.SWIPE_LEFT -> {

                        if (gameOperations.determineLeftValue()) {
                            //CORRECT ANSWER
                            FunctionsClassDebug.PrintDebug("Divisible Triggered ${divisibleTriggered}")

                            divisibleTriggered = true

                            if (divisibleTriggered) {
                                GameVariablesViewModel.TOGGLE_SNACKBAR.postValue(false)

                                FunctionsClassDebug.PrintDebug("Dismissing Hint")
                            }

                            GameVariablesViewModel.POSITIVE_POINT.postValue(3)
                            GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.postValue(3)
                        } else {
                            //WRONG ANSWER
                            FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                            functionsClassGame.playWrongSound()

                            GameVariablesViewModel.NEGATIVE_POINT.postValue(3)
                            GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.postValue(3)
                        }
                    }
                    GestureListenerConstants.SWIPE_RIGHT -> {

                        if (gameOperations.determineRightValue()) {
                            //CORRECT ANSWER
                            FunctionsClassDebug.PrintDebug("Divisible Triggered ${divisibleTriggered}")

                            divisibleTriggered = true

                            if (divisibleTriggered) {
                                GameVariablesViewModel.TOGGLE_SNACKBAR.postValue(false)

                                FunctionsClassDebug.PrintDebug("Dismissing Hint")
                            }

                            GameVariablesViewModel.POSITIVE_POINT.postValue(3)
                            GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.postValue(3)
                        } else {
                            //WRONG ANSWER
                            FunctionsClassDebug.PrintDebug("WRONG ANSWER")

                            functionsClassGame.playWrongSound()

                            GameVariablesViewModel.NEGATIVE_POINT.postValue(3)
                            GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.postValue(3)
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
                    gesturedRandomCenterView.text = "${randomCenterValue}"
                    GameVariablesViewModel.CENTER_VALUE.postValue(randomCenterValue)

                    triggerCenterRandomChange = true
                    divisibleTriggered = false
                }, 333)

                GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.postValue(GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.value!! + 1)
                GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.postValue(GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.value!! + 1)
            }
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

        if (!functionsClassMath.isNumbersDivisible(GameVariablesViewModel.CENTER_VALUE.value!!, GameVariablesViewModel.TOP_VALUE.value!!)
            && !functionsClassMath.isNumbersDivisible(GameVariablesViewModel.CENTER_VALUE.value!!, GameVariablesViewModel.LEFT_VALUE.value!!)
            && !functionsClassMath.isNumbersDivisible(GameVariablesViewModel.CENTER_VALUE.value!!, GameVariablesViewModel.RIGHT_VALUE.value!!)
            && !functionsClassMath.isNumberPrime(GameVariablesViewModel.CENTER_VALUE.value!!)) {

            val randomCenterValue: Int = listTOfRandom.random()
            gesturedRandomCenterView.text = "${randomCenterValue}"
            GameVariablesViewModel.CENTER_VALUE.postValue(randomCenterValue)

            //CORRECT ANSWER
            triggerCenterRandomChange = true

            functionsClassGame.playChangedCenterRandomSound()

            GameVariablesViewModel.POSITIVE_POINT.postValue(3)
            GameVariablesViewModel.CHANGE_CENTER_RANDOM_POSITIVE_POINT.postValue(3)
        } else {
            GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT = context.getString(R.string.thinkMore)
            GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT= context.getString(R.string.showHint)

            GameVariablesViewModel.TOGGLE_SNACKBAR.postValue(true)

            //WRONG ANSWER
            functionsClassGame.playWrongSound()

            GameVariablesViewModel.NEGATIVE_POINT.postValue(3)
            GameVariablesViewModel.CHANGE_CENTER_RANDOM_NEGATIVE_POINT.postValue(3)
        }

        functionsClassSystem.doVibrate()
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
