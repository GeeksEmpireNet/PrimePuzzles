/*
 * Copyright © 2020 By ...
 *
 * Created by Elias Fazel on 3/20/20 1:24 PM
 * Last modified 3/20/20 1:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.primepuzzles.GameView.UI

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import net.geeksempire.primepuzzles.GameData.GameInformationVariable
import net.geeksempire.primepuzzles.GameData.GameVariablesViewModel
import net.geeksempire.primepuzzles.GameData.NumbersListProvider
import net.geeksempire.primepuzzles.GameLogic.GameOperations
import net.geeksempire.primepuzzles.GameView.Utils.GestureConstants
import net.geeksempire.primepuzzles.GameView.Utils.GestureListenerConstants
import net.geeksempire.primepuzzles.GameView.Utils.GestureListenerInterface
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassGame
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassMath
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassSystem

class GesturedRandomCenterView : AppCompatButton, GestureListenerInterface {

    private var swipeGestureFilterRandomCenter: SwipeGestureFilterRandomCenter

    private val functionsClassSystem: FunctionsClassSystem = FunctionsClassSystem(context)
    private val functionsClassGame: FunctionsClassGame = FunctionsClassGame(context)
    private val functionsClassMath: FunctionsClassMath = FunctionsClassMath(context)

    private val numbersListProvider: NumbersListProvider = NumbersListProvider()

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

        swipeGestureFilterRandomCenter =
            SwipeGestureFilterRandomCenter(
                context,
                this@GesturedRandomCenterView,
                this@GesturedRandomCenterView
            )
    }

    constructor(context: Context) : super(context) {

        swipeGestureFilterRandomCenter =
            SwipeGestureFilterRandomCenter(
                context,
                this@GesturedRandomCenterView,
                this@GesturedRandomCenterView
            )
    }

    init {
        val typeface = ResourcesCompat.getFont(context, R.font.play)
        this@GesturedRandomCenterView.typeface = typeface

        val randomCenterValue: Int = numbersListProvider.generateListOfNumbers().random()
        this@GesturedRandomCenterView.text = "${randomCenterValue}"
        GameVariablesViewModel.CENTER_VALUE.value = randomCenterValue
        numbersListProvider.removeNumberFromList(randomCenterValue)
    }

    private val gameOperations: GameOperations = GameOperations(context)


    private var triggerCenterRandomChange: Boolean = false
    private var divisibleTriggered: Boolean = false


    private val springForce: SpringForce by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_LOW
            dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
        }
    }

    private val springAnimationTranslationX: SpringAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        SpringAnimation(this@GesturedRandomCenterView, DynamicAnimation.TRANSLATION_X).setSpring(springForce)
    }
    private val springAnimationTranslationY: SpringAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        SpringAnimation(this@GesturedRandomCenterView, DynamicAnimation.TRANSLATION_Y).setSpring(springForce)
    }

    private val flingAnimationX: FlingAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        FlingAnimation(this@GesturedRandomCenterView, DynamicAnimation.X)
            .setFriction(1.3f)
            .setMinValue(0f)
            .setMaxValue(context.resources.displayMetrics.widthPixels.toFloat() - (this@GesturedRandomCenterView.width))
    }
    private val flingAnimationY: FlingAnimation by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        FlingAnimation(this@GesturedRandomCenterView, DynamicAnimation.Y)
            .setFriction(1.3f)
            .setMinValue(0f)
            .setMaxValue(context.resources.displayMetrics.widthPixels.toFloat() - (this@GesturedRandomCenterView.width))
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun dispatchTouchEvent(motionEvent: MotionEvent) : Boolean {
        this.swipeGestureFilterRandomCenter.onTouchEvent(motionEvent)

        return super.dispatchTouchEvent(motionEvent)
    }

    override fun onSwipeGesture (gestureConstants: GestureConstants,
                                 downMotionEvent: MotionEvent, moveMotionEvent: MotionEvent,
                                 initVelocityX: Float, initVelocityY: Float) {

        when (gestureConstants) {
            is GestureConstants.SwipeHorizontal -> {

                flingAnimationX.setStartValue(this@GesturedRandomCenterView.x)
                flingAnimationX.setStartVelocity(initVelocityX)
                flingAnimationX.start()

                flingAnimationX.addEndListener { dynamicAnimation, canceled, value, velocity ->
                    when (gestureConstants.horizontalDirection) {
                        GestureListenerConstants.SWIPE_LEFT -> {
                            if (gameOperations.determineLeftValue()) {
                                //CORRECT ANSWER
                                divisibleTriggered = true

                                if (divisibleTriggered) {
                                    GameVariablesViewModel.TOGGLE_SNACKBAR.postValue(false)

                                }

                                GameVariablesViewModel.POSITIVE_POINT.postValue(3)
                                GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.postValue(3)
                            } else {
                                //WRONG ANSWER
                                functionsClassGame.playWrongSound()

                                GameVariablesViewModel.NEGATIVE_POINT.postValue(3)
                                GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.postValue(3)
                            }
                        }
                        GestureListenerConstants.SWIPE_RIGHT -> {
                            if (gameOperations.determineRightValue()) {
                                //CORRECT ANSWER

                                divisibleTriggered = true

                                if (divisibleTriggered) {
                                    GameVariablesViewModel.TOGGLE_SNACKBAR.postValue(false)

                                }

                                GameVariablesViewModel.POSITIVE_POINT.postValue(3)
                                GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.postValue(3)
                            } else {
                                //WRONG ANSWER
                                functionsClassGame.playWrongSound()

                                GameVariablesViewModel.NEGATIVE_POINT.postValue(3)
                                GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.postValue(3)
                            }
                        }
                    }

                    Handler().postDelayed({
                        springAnimationTranslationX.start()

                        val randomCenterValue: Int = numbersListProvider.generateListOfNumbers().random()
                        this@GesturedRandomCenterView.text = "${randomCenterValue}"
                        GameVariablesViewModel.CENTER_VALUE.postValue(randomCenterValue)
                        numbersListProvider.removeNumberFromList(randomCenterValue)

                        triggerCenterRandomChange = true
                        divisibleTriggered = false
                    }, 333)

                    GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.postValue(GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.value!! + 1)
                    GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.postValue(GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.value!! + 1)
                }
            }

            is GestureConstants.SwipeVertical -> {

                flingAnimationY.setStartValue(this@GesturedRandomCenterView.y)
                flingAnimationY.setStartVelocity(initVelocityY)
                flingAnimationY.start()

                flingAnimationY.addEndListener { dynamicAnimation, canceled, value, velocity ->
                    when (gestureConstants.verticallDirection) {
                        GestureListenerConstants.SWIPE_DOWN -> {
                            if (gameOperations.determinePrimeValue()) {
                                //CORRECT ANSWER
                                GameVariablesViewModel.POSITIVE_POINT.postValue(13)
                                GameVariablesViewModel.PRIME_POSITIVE_POINT.postValue(13)


                                GameVariablesViewModel.PRIME_NUMBER_DETECTED.postValue(true)

                                GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT = context.getString(R.string.primeDetect)
                                GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT= context.getString(R.string.primeDetectAction)

                                GameVariablesViewModel.TOGGLE_SNACKBAR.postValue(true)
                                GameInformationVariable.snackBarAction = GameInformationVariable.PRIME_NUMBER_ACTION
                            } else {
                                //WRONG ANSWER
                                functionsClassGame.playWrongSound()

                                GameVariablesViewModel.NEGATIVE_POINT.postValue(13)
                                GameVariablesViewModel.PRIME_NEGATIVE_POINT.postValue(13)
                            }
                        }
                        GestureListenerConstants.SWIPE_UP -> {
                            if (gameOperations.determineTopValue()) {
                                divisibleTriggered = true

                                if (divisibleTriggered) {
                                    GameVariablesViewModel.TOGGLE_SNACKBAR.postValue(false)

                                }

                                //CORRECT ANSWER
                                GameVariablesViewModel.POSITIVE_POINT.postValue(3)
                                GameVariablesViewModel.DIVISIBLE_POSITIVE_POINT.postValue(3)
                            } else {
                                //WRONG ANSWER
                                functionsClassGame.playWrongSound()

                                GameVariablesViewModel.NEGATIVE_POINT.postValue(3)
                                GameVariablesViewModel.DIVISIBLE_NEGATIVE_POINT.postValue(3)
                            }
                        }
                    }

                    Handler().postDelayed({
                        springAnimationTranslationY.start()

                        val randomCenterValue: Int = numbersListProvider.generateListOfNumbers().random()
                        this@GesturedRandomCenterView.text = "${randomCenterValue}"
                        GameVariablesViewModel.CENTER_VALUE.postValue(randomCenterValue)
                        numbersListProvider.removeNumberFromList(randomCenterValue)

                        triggerCenterRandomChange = true
                        divisibleTriggered = false
                    }, 333)

                    GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.postValue(GameVariablesViewModel.SHUFFLE_PROCESS_POSITION.value!! + 1)
                    GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.postValue(GameVariablesViewModel.SHUFFLE_PROCESS_VALUE.value!! + 1)
                }
            }
        }
    }

    override fun onLongPress(motionEvent: MotionEvent) {

        if (!functionsClassMath.isNumbersDivisible(GameVariablesViewModel.CENTER_VALUE.value!!, GameVariablesViewModel.TOP_VALUE.value!!)
            && !functionsClassMath.isNumbersDivisible(GameVariablesViewModel.CENTER_VALUE.value!!, GameVariablesViewModel.LEFT_VALUE.value!!)
            && !functionsClassMath.isNumbersDivisible(GameVariablesViewModel.CENTER_VALUE.value!!, GameVariablesViewModel.RIGHT_VALUE.value!!)
            && !functionsClassMath.isNumberPrime(GameVariablesViewModel.CENTER_VALUE.value!!)) {

            val randomCenterValue: Int = numbersListProvider.generateListOfNumbers().random()
            this@GesturedRandomCenterView.text = "${randomCenterValue}"
            GameVariablesViewModel.CENTER_VALUE.postValue(randomCenterValue)
            numbersListProvider.removeNumberFromList(randomCenterValue)

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
}