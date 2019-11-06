package net.geeksempire.primepuzzles.Utils.UI

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
import net.geeksempire.physics.animation.Core.SimpleSpringListener
import net.geeksempire.physics.animation.Core.Spring
import net.geeksempire.physics.animation.Core.SpringConfig
import net.geeksempire.physics.animation.SpringSystem
import net.geeksempire.primepuzzles.GameInformation.GameInformationVariable
import net.geeksempire.primepuzzles.GameLogic.GameLevel
import net.geeksempire.primepuzzles.GameLogic.GameOperations
import net.geeksempire.primepuzzles.GameLogic.GameVariables
import net.geeksempire.primepuzzles.R
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassDebug
import net.geeksempire.primepuzzles.Utils.FunctionsClass.FunctionsClassUI
import net.geeksempire.primepuzzles.Utils.FunctionsClass.isNumberPrime
import net.geeksempire.primepuzzles.Utils.FunctionsClass.isNumbersDivisible
import kotlin.math.abs

class SwipeGestureFilterRandomCenter(private val view: Button, initContext: Context, private val gestureListener: GestureListener) : SimpleOnGestureListener() {

    private val context: Context = initContext

    lateinit var functionsClassUI: FunctionsClassUI


    private var swipeMinDistance: Int  = 10
    private var swipeMaxDistance: Int  = 1000

    private var swipeMinVelocity: Int  = 10

    private var mode: Int = MODE_DYNAMIC

    private var swipeMode: Int = 0

    private var running: Boolean = true


    private var tapIndicator = false
    private val gestureDetector: GestureDetector


    private var triggerCenterRandomChange: Boolean = false
    private var primeNumberDetected: Boolean = false
    private var divisibleTriggered: Boolean = false

    init {
        this.gestureDetector = GestureDetector(initContext, this)

        functionsClassUI = FunctionsClassUI(initContext)
    }

    interface GestureListener {
        fun onSwipe(direction: Int)

        fun onSingleTapUp()
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
                .setMaxValue(context.resources.displayMetrics.widthPixels.toFloat() - view.width)
        }

        val flingAnimationY: FlingAnimation by lazy(LazyThreadSafetyMode.NONE) {
            FlingAnimation(view, DynamicAnimation.Y)
                .setFriction(1.3f)
                .setMinValue(0f)
                .setMaxValue(context.resources.displayMetrics.heightPixels.toFloat() - view.height)
        }

        flingAnimationX.addEndListener { animation, canceled, value, velocity ->

            when (swipeMode()) {
                SwipeGestureFilterRandomCenter.SWIPE_LEFT -> {
                    if (GameOperations().determineLeftValue()) {
                        divisibleTriggered = true

                        if (divisibleTriggered) {
                            GameVariables.TOGGLE_SNACKBAR.value = false

                            FunctionsClassDebug.PrintDebug("Dismissing Hint")
                        }

                        FunctionsClassDebug.PrintDebug("Divisible Triggered ${divisibleTriggered}")
                    }

                }
                SwipeGestureFilterRandomCenter.SWIPE_RIGHT -> {
                    if (GameOperations().determineRightValue()) {
                        divisibleTriggered = true

                        if (divisibleTriggered) {
                            GameVariables.TOGGLE_SNACKBAR.value = false

                            FunctionsClassDebug.PrintDebug("Dismissing Hint")
                        }

                        FunctionsClassDebug.PrintDebug("Divisible Triggered ${divisibleTriggered}")
                    }
                }
            }

            Handler()
                .postDelayed({
                    springAnimationTranslationX.start()
                    springAnimationTranslationY.start()

                    if ((!isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.TOP_VALUE.value!!)
                                && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.LEFT_VALUE.value!!)
                                && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.RIGHT_VALUE.value!!))
                        || divisibleTriggered) {

                        val listTOfRandom = ArrayList<Int>()
                        when (GameLevel().getGameDifficultyLevel()) {
                            GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {
                                listTOfRandom.addAll(2..9)
                            }
                            GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {
                                listTOfRandom.addAll(10..99)
                            }
                        }

                        val randomCenterValue: Int = listTOfRandom.random()
                        view.text = "${randomCenterValue}"
                        GameVariables.CENTER_VALUE.value = randomCenterValue

                        triggerCenterRandomChange = true
                        divisibleTriggered = false
                    }
                }, 333)
        }

        flingAnimationY.addEndListener { animation, canceled, value, velocity ->

            when (swipeMode()) {
                SwipeGestureFilterRandomCenter.SWIPE_UP -> {
                    if (GameOperations().determineTopValue()) {
                        divisibleTriggered = true

                        if (divisibleTriggered) {
                            GameVariables.TOGGLE_SNACKBAR.value = false

                            FunctionsClassDebug.PrintDebug("Dismissing Hint")
                        }

                        FunctionsClassDebug.PrintDebug("Divisible Triggered ${divisibleTriggered}")
                    }
                }
                SwipeGestureFilterRandomCenter.SWIPE_DOWN -> {

                    if (isNumberPrime(GameVariables.CENTER_VALUE.value!!)) {
                        FunctionsClassDebug.PrintDebug("${GameVariables.CENTER_VALUE.value} IS A PRIME Number")

                        primeNumberDetected = true


                    } else {
                        if ((!isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.TOP_VALUE.value!!)
                                    && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.LEFT_VALUE.value!!)
                                    && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.RIGHT_VALUE.value!!))) {

                        } else {
                            GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT = context.getString(R.string.thinkMore)
                            GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT= context.getString(R.string.showHint)

                            GameVariables.TOGGLE_SNACKBAR.value = true
                        }
                    }
                }
            }


            if (primeNumberDetected) {
                GameVariables.PRIME_NUMBER_DETECTED.value = true

                GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT = context.getString(R.string.primeDetect)
                GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT= context.getString(R.string.primeDetectAction)

                GameVariables.TOGGLE_SNACKBAR.value = true
                GameInformationVariable.snackBarAction = GameInformationVariable.PRIME_NUMBER_ACTION

                primeNumberDetected = false
            } else {

                Handler()
                    .postDelayed({
                        springAnimationTranslationX.start()
                        springAnimationTranslationY.start()

                        if ((!isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.TOP_VALUE.value!!)
                                    && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.LEFT_VALUE.value!!)
                                    && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.RIGHT_VALUE.value!!))
                            || divisibleTriggered
                            || primeNumberDetected) {

                            val listTOfRandom = ArrayList<Int>()
                            when (GameLevel().getGameDifficultyLevel()) {
                                GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {
                                    listTOfRandom.addAll(2..9)
                                }
                                GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {
                                    listTOfRandom.addAll(10..99)
                                }
                            }

                            val randomCenterValue: Int = listTOfRandom.random()
                            view.text = "${randomCenterValue}"
                            GameVariables.CENTER_VALUE.value = randomCenterValue

                            triggerCenterRandomChange = true
                            divisibleTriggered = false
                            primeNumberDetected = false
                        }
                    }, 333)
            }
        }

        var result = false
        if (abs(velocityY) >= this.swipeMinVelocity && yDistance > this.swipeMinDistance && xDistance < yDistance) {//Vertical
            if (downMotionEvent.y > moveMotionEvent.y) {//Bottom -> Up
                this.gestureListener.onSwipe(SWIPE_UP)
                swipeMode = SwipeGestureFilterRandomCenter.SWIPE_UP


            } else {//Up -> Bottom
                this.gestureListener.onSwipe(SWIPE_DOWN)
                swipeMode = SwipeGestureFilterRandomCenter.SWIPE_DOWN


            }

            flingAnimationY.setStartValue(view.y)
            flingAnimationY.setStartVelocity(velocityY)
            flingAnimationY.start()

            result = true
        }

        if (abs(velocityX) >= this.swipeMinVelocity && xDistance > this.swipeMinDistance && yDistance < xDistance) {//Horizontal
            if (downMotionEvent.x > moveMotionEvent.x) {//Right -> Left
                this.gestureListener.onSwipe(SWIPE_LEFT)
                swipeMode = SwipeGestureFilterRandomCenter.SWIPE_LEFT


            } else {//Left -> Right
                this.gestureListener.onSwipe(SWIPE_RIGHT)
                swipeMode = SwipeGestureFilterRandomCenter.SWIPE_RIGHT


            }

            flingAnimationX.setStartValue(view.x)
            flingAnimationX.setStartVelocity(velocityX)
            flingAnimationX.start()

            result = true
        }
        return result
    }

    override fun onSingleTapConfirmed(motionEvent: MotionEvent): Boolean {
        this.gestureListener.onSingleTapUp()

        Handler().postDelayed({
            val listTOfRandom = ArrayList<Int>()
            when (GameLevel().getGameDifficultyLevel()) {
                GameLevel.GAME_DIFFICULTY_LEVEL_ONE_DIGIT -> {
                    listTOfRandom.addAll(2..9)
                }
                GameLevel.GAME_DIFFICULTY_LEVEL_TWO_DIGIT -> {
                    listTOfRandom.addAll(10..99)
                }
            }

            if (!isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.TOP_VALUE.value!!)
                && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.LEFT_VALUE.value!!)
                && !isNumbersDivisible(GameVariables.CENTER_VALUE.value!!, GameVariables.RIGHT_VALUE.value!!)
                && !isNumberPrime(GameVariables.CENTER_VALUE.value!!)) {

                val randomCenterValue: Int = listTOfRandom.random()
                view.text = "${randomCenterValue}"
                GameVariables.CENTER_VALUE.value = randomCenterValue

                triggerCenterRandomChange = true
            } else {
                GameInformationVariable.SNACKBAR_HINT_INFORMATION_TEXT = context.getString(R.string.thinkMore)
                GameInformationVariable.SNACKBAR_HINT_BUTTON_TEXT= context.getString(R.string.showHint)

                GameVariables.TOGGLE_SNACKBAR.value = true
            }
        }, 123)

        return false
    }



    fun onTouchEvent(motionEvent: MotionEvent) {
        if (!this.running) {
            return
        }

        val TENSION = 800.0
        val DAMPER = 20.0 //friction

        val springSystem = SpringSystem.create()
        val spring = springSystem.createSpring()

        spring.addListener(object : SimpleSpringListener(/*spring*/) {
            override fun onSpringUpdate(spring: Spring?) {
                val value = spring!!.currentValue.toFloat()
                val scale = 1f - (value * 0.5f)
                view.scaleX = scale
                view.scaleY = scale
            }

            override fun onSpringEndStateChange(spring: Spring?) {

            }

            override fun onSpringAtRest(spring: Spring?) {

            }

            override fun onSpringActivate(spring: Spring?) {

            }

        })
        val springConfig = SpringConfig(TENSION, DAMPER)
        spring.springConfig = springConfig

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

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                spring.endValue = (0.7)

            }
            MotionEvent.ACTION_UP -> {
                spring.endValue = (0.1)

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
